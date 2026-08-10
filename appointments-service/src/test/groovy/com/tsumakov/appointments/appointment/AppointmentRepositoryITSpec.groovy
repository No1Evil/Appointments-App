package dev.tsumakov.appointments.appointment

import dev.tsumakov.appointments.AbstractPostgresITSpec
import dev.tsumakov.appointments.appointment.status.AppointmentStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.springframework.jdbc.core.JdbcTemplate

import java.time.OffsetDateTime

@MicronautTest
class AppointmentRepositoryITSpec extends AbstractPostgresITSpec {

    @Inject
    AppointmentRepository repository

    @Inject
    JdbcTemplate jdbcTemplate

    UUID patientId = UUID.randomUUID()
    UUID practitionerId = UUID.randomUUID()
    OffsetDateTime start = OffsetDateTime.parse("2026-02-02T09:00:00+02:00")
    OffsetDateTime end = start.plusMinutes(30)
    Long slotId
    List<Long> createdSlotIds = []

    def setup() {
        jdbcTemplate.update("insert into patients (id, first_name, last_name) values (?, ?, ?)", patientId, "John", "Doe")
        jdbcTemplate.update("insert into practitioners (id, first_name, last_name, service_code) values (?, ?, ?, ?)", practitionerId, "Jane", "Smith", "dental")
        slotId = createSlot("free", start, end)
    }

    def cleanup() {
        jdbcTemplate.update("delete from appointments where patient_id = ?", patientId)
        createdSlotIds.each { id -> jdbcTemplate.update("delete from slots where id = ?", id) }
        jdbcTemplate.update("delete from patients where id = ?", patientId)
        jdbcTemplate.update("delete from practitioners where id = ?", practitionerId)
    }

    void "should create and find an appointment with joined names"() {
        given:
        def appointment = newAppointment(comment: null)

        when:
        repository.create(appointment)
        def found = repository.findBy(appointment.id)

        then:
        found.present
        def result = found.get()
        result.id == appointment.id
        result.slotId == slotId
        result.patientId == patientId
        result.practitionerId == practitionerId
        result.serviceName == "dental"
        result.patientName == "John Doe"
        result.practitionerName == "Jane Smith"
        result.startTime.toInstant() == start.toInstant()
        result.endTime.toInstant() == end.toInstant()
        result.comment == null
        result.status == AppointmentStatus.SCHEDULED
    }

    void "should return empty when appointment not found"() {
        expect:
        repository.findBy(UUID.randomUUID()).empty
    }

    void "should return all appointments with joined names"() {
        given:
        def appointment = newAppointment()
        repository.create(appointment)

        when:
        def all = repository.findAll()

        then:
        all*.id.contains(appointment.id)
        def created = all.find { it.id == appointment.id }
        created.patientName == "John Doe"
        created.practitionerName == "Jane Smith"
    }

    void "should update an appointment"() {
        given:
        def appointment = newAppointment(comment: "old comment")
        repository.create(appointment)
        appointment.updateComment("new comment")
        appointment.updateService("gp")

        when:
        def updated = repository.update(appointment)
        def found = repository.findBy(appointment.id)

        then:
        updated
        found.present
        found.get().comment == "new comment"
        found.get().serviceName == "gp"
        found.get().status == AppointmentStatus.SCHEDULED
    }

    void "should delete an appointment"() {
        given:
        def appointment = newAppointment()
        repository.create(appointment)

        when:
        def deleted = repository.delete(appointment.id)
        def found = repository.findBy(appointment.id)

        then:
        deleted
        found.empty
    }

    void "should find overlapping appointments for a patient, excluding self and cancelled"() {
        given: "appointments with overlapping, cancelled and non-overlapping times"
        def existing = newAppointment()
        repository.create(existing)

        def overlapSlot = createSlot("free", start.plusMinutes(5), end.plusMinutes(5))
        def overlapping = newAppointment(slotId: overlapSlot, startTime: start.plusMinutes(5), endTime: end.plusMinutes(5))
        repository.create(overlapping)

        def cancelledSlot = createSlot("free", start.plusMinutes(10), end.plusMinutes(10))
        def cancelled = newAppointment(slotId: cancelledSlot, startTime: start.plusMinutes(10), endTime: end.plusMinutes(10), status: AppointmentStatus.CANCELLED)
        repository.create(cancelled)

        def laterSlot = createSlot("free", start.plusHours(3), start.plusHours(3).plusMinutes(30))
        def later = newAppointment(slotId: laterSlot, startTime: start.plusHours(3), endTime: start.plusHours(3).plusMinutes(30))
        repository.create(later)

        when:
        def withCurrent = repository.findUsersOverlappingAppointments(patientId, practitionerId, start.minusMinutes(10), end.plusMinutes(10), existing.id)
        def withoutCurrent = repository.findUsersOverlappingAppointments(patientId, practitionerId, start.minusMinutes(10), end.plusMinutes(10), null)

        then:
        withCurrent*.id.sort() == [overlapping.id].sort()
        withoutCurrent*.id.sort() == [existing.id, overlapping.id].sort()
        !withoutCurrent*.id.contains(cancelled.id)
        !withoutCurrent*.id.contains(later.id)
    }

    void "should filter appointments by all criteria"() {
        given: "appointments varying by practitioner, status, service and patient"
        def match = newAppointment(comment: "match")
        repository.create(match)

        def otherPractitionerId = UUID.randomUUID()
        jdbcTemplate.update("insert into practitioners (id, first_name, last_name, service_code) values (?, ?, ?, ?)", otherPractitionerId, "Bob", "Brown", "gp")
        def otherSlot = createSlot("free", start.plusHours(1), start.plusHours(1).plusMinutes(30))
        repository.create(newAppointment(slotId: otherSlot, startTime: start.plusHours(1), endTime: start.plusHours(1).plusMinutes(30), practitionerId: otherPractitionerId, serviceName: "gp", comment: "other practitioner"))
        repository.create(newAppointment(slotId: otherSlot, startTime: start.plusHours(1), endTime: start.plusHours(1).plusMinutes(30), serviceName: "gp", comment: "other service"))
        repository.create(newAppointment(status: AppointmentStatus.COMPLETED, comment: "other status"))
        def otherPatientId = UUID.randomUUID()
        jdbcTemplate.update("insert into patients (id, first_name, last_name) values (?, ?, ?)", otherPatientId, "Alice", "Green")
        repository.create(newAppointment(patientId: otherPatientId, comment: "other patient"))

        when:
        def result = repository.filterBy(new AppointmentsParams(practitionerId, AppointmentStatus.SCHEDULED, "dental", patientId))

        then:
        result*.id == [match.id]
    }

    private Long createSlot(String status, OffsetDateTime slotStart, OffsetDateTime slotEnd) {
        def id = jdbcTemplate.queryForObject(
                "insert into slots (status, service_code, start_time, end_time) values (?, 'dental', ?, ?) returning id",
                Long.class, status, slotStart, slotEnd)
        createdSlotIds << id
        id
    }

    private Appointment newAppointment(Map overrides = [:]) {
        Appointment.builder()
                .id(overrides.getOrDefault("id", UUID.randomUUID()) as UUID)
                .slotId(overrides.getOrDefault("slotId", slotId) as Long)
                .patientId(overrides.getOrDefault("patientId", patientId) as UUID)
                .practitionerId(overrides.getOrDefault("practitionerId", practitionerId) as UUID)
                .serviceName(overrides.getOrDefault("serviceName", "dental") as String)
                .startTime(overrides.getOrDefault("startTime", start) as OffsetDateTime)
                .endTime(overrides.getOrDefault("endTime", end) as OffsetDateTime)
                .comment(overrides.getOrDefault("comment", "some comment") as String)
                .status(overrides.getOrDefault("status", AppointmentStatus.SCHEDULED) as AppointmentStatus)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build()
    }
}
