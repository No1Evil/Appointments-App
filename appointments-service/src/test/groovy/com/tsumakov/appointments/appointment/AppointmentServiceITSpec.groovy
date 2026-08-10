package dev.tsumakov.appointments.appointment

import dev.tsumakov.appointments.AbstractPostgresITSpec
import dev.tsumakov.appointments.appointment.web.request.SubmitAppointmentRequest
import dev.tsumakov.appointments.slot.exception.SlotIsTakenException
import io.github.robsonkades.uuidv7.UUIDv7
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.springframework.jdbc.core.JdbcTemplate

import java.time.OffsetDateTime
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@MicronautTest(transactional = false)
class AppointmentServiceITSpec extends AbstractPostgresITSpec {

    @Inject
    AppointmentService appointmentService

    @Inject
    JdbcTemplate jdbcTemplate

    UUID practitionerId = UUIDv7.randomUUID()
    List<Long> createdSlotIds = []
    List<UUID> createdPatientIds = []

    def setup() {
        jdbcTemplate.update("insert into practitioners (id, first_name, last_name, service_code) values (?, ?, ?, ?)", practitionerId, "Jane", "Smith", "dental")
    }

    def cleanup() {
        createdSlotIds.each { id -> jdbcTemplate.update("delete from appointments where slot_id = ?", id) }
        createdSlotIds.each { id -> jdbcTemplate.update("delete from slots where id = ?", id) }
        createdPatientIds.each { id -> jdbcTemplate.update("delete from patients where id = ?", id) }
        jdbcTemplate.update("delete from practitioners where id = ?", practitionerId)
    }

    void "concurrent submissions for the same free slot must result in exactly one booking"() {
        given: "several free slots"
        def slotIds = (1..5).collect { createFreeSlot(it) }

        when: "multiple threads race to book each slot simultaneously"
        def outcomes = slotIds.collect { raceBookings(it) }

        then: "every race has exactly one winner and all losers are rejected because the slot is taken"
        outcomes.every { it.successes == 1 }
        outcomes.every { it.taken == 7 }
        outcomes.every { it.failures == 0 }

        and: "every slot has exactly one appointment row"
        slotIds.every { id ->
            jdbcTemplate.queryForObject("select count(*) from appointments where slot_id = ?", Long, id) == 1
        }
    }

    /**
     * Simulates concurrent bookings of the same slot. A lock on the slot row is held
     * on a separate connection while the workers run: a plain SELECT does not block on
     * a row lock, so every worker passes the "slot is free" check and then blocks on
     * the booking UPDATE. Once the lock is released all blocked UPDATEs apply, so any
     * booking path that only reads the status before updating will double-book.
     */
    private Map<String, Integer> raceBookings(Long slotId) {
        def threadCount = 8
        def patientIds = (1..threadCount).collect { UUID.randomUUID() }
        createdPatientIds.addAll(patientIds)
        patientIds.each { pid ->
            jdbcTemplate.update("insert into patients (id, first_name, last_name) values (?, ?, ?)", pid, "Alice", "White")
        }

        def startGate = new CountDownLatch(1)
        def done = new CountDownLatch(threadCount)
        def successes = new AtomicInteger(0)
        def taken = new AtomicInteger(0)
        def failures = new AtomicInteger(0)
        def failureMessages = Collections.synchronizedList(new ArrayList<String>())

        def executor = Executors.newFixedThreadPool(threadCount)
        patientIds.each { patientId ->
            executor.submit {
                startGate.await()
                try {
                    appointmentService.submit(new SubmitAppointmentRequest(slotId, patientId, practitionerId, "race"))
                    successes.incrementAndGet()
                } catch (SlotIsTakenException e) {
                    taken.incrementAndGet()
                } catch (Throwable t) {
                    failures.incrementAndGet()
                    failureMessages << t.toString()
                } finally {
                    done.countDown()
                }
            }
        }

        def lock = jdbcTemplate.dataSource.connection
        lock.autoCommit = false
        def locked = lock.prepareStatement("select * from slots where id = ? for update")
        locked.setLong(1, slotId)
        locked.executeQuery().next()

        def lockHeld = false
        try {
            jdbcTemplate.execute("select * from slots where id = ${slotId} for update nowait")
        } catch (Throwable t) {
            lockHeld = true
        }
        if (!lockHeld) {
            throw new IllegalStateException("Slot row lock was not acquired for slot $slotId")
        }

        startGate.countDown()
        sleep(500)
        lock.rollback()
        lock.close()

        if (!done.await(30, TimeUnit.SECONDS)) {
            throw new IllegalStateException("Concurrent submissions did not finish within 30 seconds")
        }
        executor.shutdownNow()

        [successes: successes.get(), taken: taken.get(), failures: failures.get(), failureMessages: failureMessages.toList()]
    }

    private Long createFreeSlot(int seq) {
        def start = OffsetDateTime.now().plusDays(1).withSecond(0).withNano(0).plusMinutes(seq * 60)
        def id = jdbcTemplate.queryForObject(
                "insert into slots (status, service_code, start_time, end_time) values ('free', 'dental', ?, ?) returning id",
                Long.class, start, start.plusMinutes(30))
        createdSlotIds << id
        id
    }
}
