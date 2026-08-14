package dev.tsumakov.appointments.slot

import dev.tsumakov.appointments.AbstractPostgresITSpec
import dev.tsumakov.appointments.service.ServiceCategory
import dev.tsumakov.appointments.slot.status.SlotStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.springframework.jdbc.core.JdbcTemplate

import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@MicronautTest
class SlotRepositoryITSpec extends AbstractPostgresITSpec {

    static final Random random = new Random()

    @Inject
    SlotRepository repository

    @Inject
    JdbcTemplate jdbcTemplate

    ServiceCategory serviceCategory = GroovyMock(
            ServiceCategory,
            constructorArgs: ["gp", "General Practice"]
    )

    def setup() {
        jdbcTemplate.execute("delete from slots")
    }

    def createSlot(
            Long id = random.nextLong(),
            SlotStatus status = SlotStatus.FREE,
            OffsetDateTime startTime = OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS),
            OffsetDateTime endTime = OffsetDateTime.now().plusHours(1).truncatedTo(ChronoUnit.MICROS)
    ) {
        return Slot.builder()
                .id(id)
                .status(status)
                .service(serviceCategory)
                .startTime(startTime)
                .endTime(endTime)
                .build()
    }

    void "should create and find a slot"() {
        given:
        def slot = createSlot(null)

        when:
        def createdId = repository.create(slot)
        def found = repository.findById(createdId)

        then:
        found.present
        def result = found.get()

        then:
        result.status == slot.status
        result.service == slot.service
        result.startTime.toInstant() == slot.startTime.toInstant()
        result.endTime.toInstant() == slot.endTime.toInstant()
    }

    void "should return empty when slot not found"() {
        when:
        var result = repository.findById(random.nextLong())

        then:
        result.isEmpty()
    }

    void "should return empty list on empty table"() {
        expect:
        repository.findAll().isEmpty()
    }

    void "should list after creating"() {
        given:
        def slot = createSlot(null)

        when:
        repository.create(slot)

        then:
        repository.findAll().size() == 1
    }

    void "should delete slot successfully"() {
        given:
        def slot = createSlot()

        when:
        def generatedId = repository.create(slot)
        repository.delete(generatedId)

        then:
        repository.findById(generatedId).isEmpty()
    }

    void "should not delete unexisting slot"() {
        when:
        def isDeleted = repository.delete(random.nextLong())

        then:
        !isDeleted
    }

    void "should delete slot"() {
        given:
        def slot = createSlot(null)

        when:
        def generatedId = repository.create(slot)
        repository.delete(generatedId)

        then:
        repository.findById(generatedId).isEmpty()
        repository.findAll().isEmpty()
    }

    void "should not update unexisting slot"() {
        given:
        def slot = createSlot()

        when:
        def isUpdated = repository.update(slot)

        then:
        !isUpdated
    }

    void "should update slot"() {
        given:
        def slot = createSlot(null)

        when:
        def generatedId = repository.create(slot)
        def newSlotObj = createSlot(generatedId, SlotStatus.BOOKED)
        repository.update(newSlotObj)

        and:
        def savedSlot = repository.findById(generatedId)

        then:
        savedSlot.isPresent()
        def result = savedSlot.get()

        and:
        result.id == generatedId
        result.status == SlotStatus.BOOKED
        result.startTime.toInstant() == newSlotObj.startTime.toInstant()
        result.endTime.toInstant() == newSlotObj.endTime.toInstant()
    }

    void "should list slots that are free"() {
        given:
        def slot1 = createSlot(null, SlotStatus.FREE)
        def slot2 = createSlot(null, SlotStatus.FREE)
        def slot3 = createSlot(null, SlotStatus.BOOKED)

        when:
        repository.create(slot1)
        repository.create(slot2)
        repository.create(slot3)

        and:
        def filter = new SlotParams(SlotStatus.FREE, null, null)
        def result = repository.listByFilter(filter)

        then:
        !result.isEmpty()
        result.size() == 2
    }

    void "should list slots that are booked"() {
        given:
        def slot1 = createSlot(null, SlotStatus.FREE)
        def slot2 = createSlot(null, SlotStatus.BOOKED)
        def slot3 = createSlot(null, SlotStatus.BOOKED)

        when:
        repository.create(slot1)
        repository.create(slot2)
        repository.create(slot3)

        and:
        def filter = new SlotParams(SlotStatus.BOOKED, null, null)
        def result = repository.listByFilter(filter)

        then:
        !result.isEmpty()
        result.size() == 2
    }


}
