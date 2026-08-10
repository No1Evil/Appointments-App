package dev.tsumakov.appointments.slot

import dev.tsumakov.appointments.slot.exception.SlotIsTakenException
import dev.tsumakov.appointments.slot.exception.SlotValidationException
import dev.tsumakov.appointments.slot.status.SlotStatus
import spock.lang.Specification

import java.time.OffsetDateTime

class SlotSpec extends Specification {

    static final Random random = new Random()

    static def createSlot(
            Long id = random.nextLong(),
            SlotStatus status = SlotStatus.FREE,
            OffsetDateTime startTime = OffsetDateTime.now(),
            OffsetDateTime endTime = OffsetDateTime.now().plusHours(1)
    ) {
        return Slot.builder()
                .id(id)
                .status(status)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    def "equals and hashCode should only compare the id field"() {
        given: "two slots with the same id but different names"
        def id = random.nextLong()
        def slot1 = createSlot(id)
        def slot2 = createSlot(id, SlotStatus.BOOKED)

        and: "a slot with a different code"
        def slot3 = createSlot(random.nextLong())

        expect: "slots with the same code to be equal and have the same hashCode"
        slot1 == slot2
        slot1.hashCode() == slot2.hashCode()

        and: "slots with different codes to not be equal"
        slot1 != slot3
    }

    def "should validate not expired"() {
        given: "a slot"
        def slot = createSlot(
                random.nextLong(),
                SlotStatus.FREE,
                OffsetDateTime.now().plusHours(1),
                OffsetDateTime.now().plusHours(2)
        )

        when: "we validate it"
        slot.validateNotExpired()

        then: "should not throw a error"
        noExceptionThrown()
    }

    def "should throw a error then validating expired"() {
        given: "a slot"
        def slot = createSlot(
                random.nextLong(),
                SlotStatus.FREE,
                OffsetDateTime.now().minusHours(1),
                OffsetDateTime.now().minusHours(2)
        )

        when: "we validate it"
        slot.validateNotExpired()

        then: "should throw SlotValidationException"
        thrown(SlotValidationException)
    }

    def "should mark slot booked"() {
        given: "a slot"
        def slot = createSlot()

        when: "we mark it booked"
        slot.markAsBooked()

        then: "should not throw exception"
        noExceptionThrown()

        and: "should mark as booked"
        slot.getStatus() == SlotStatus.BOOKED
    }

    def "should throw exception when booking already booked slot"() {
        given: "a booked slot"
        def slot = createSlot(random.nextLong(), SlotStatus.BOOKED)

        when: "we mark it booked"
        slot.markAsBooked()

        then: "should throw SlotIsTakenException"
        thrown(SlotIsTakenException)
    }

    def "should throw exception when booking blocked slot"() {
        given: "a blocked slot"
        def slot = createSlot(random.nextLong(), SlotStatus.BLOCKED)

        when: "we mark it booked"
        slot.markAsBooked()

        then: "should throw SlotIsTakenException"
        thrown(SlotIsTakenException)
    }

    def "should mark slot free if it is booked"() {
        given: "a booked slot"
        def slot = createSlot(random.nextLong(), SlotStatus.BOOKED)

        when: "we mark it free"
        slot.markAsFree()

        then: "should not throw exception"
        noExceptionThrown()

        and: "should change the status"
        slot.status == SlotStatus.FREE
    }

    def "should throw when marking blocked slot free"() {
        given: "a blocked slot"
        def slot = createSlot(random.nextLong(), SlotStatus.BLOCKED)

        when: "we mark it free"
        slot.markAsFree()

        then: "should throw SlotValidationException"
        thrown(SlotValidationException)
    }

    def "should not throw when marking free slot free"() {
        given: "a free slot"
        def slot = createSlot(random.nextLong(), SlotStatus.FREE)

        when: "we mark it free"
        slot.markAsFree()

        then: "status is unchanged"
        slot.status == SlotStatus.FREE
    }


}
