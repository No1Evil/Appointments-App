package dev.tsumakov.appointments.appointment

import dev.tsumakov.appointments.appointment.status.AppointmentStatus
import dev.tsumakov.appointments.common.exception.StringValidationException
import dev.tsumakov.appointments.common.factory.UuidFactory
import spock.lang.Specification
import java.time.OffsetDateTime

class AppointmentFactorySpec extends Specification {

    UuidFactory uuidFactory = Mock()
    AppointmentFactory factory = new AppointmentFactory(uuidFactory)

    void "should create appointment with all fields correctly"() {
        given: "Input data"
        def slotId = 100L
        def patientId = UUID.randomUUID()
        def practitionerId = UUID.randomUUID()
        def serviceName = "Therapy"
        def comment = "Some-sort-of-comment"
        def start = OffsetDateTime.now()
        def end = start.plusHours(1)
        def status = AppointmentStatus.SCHEDULED
        def expectedUuid = UUID.randomUUID()

        and: "Mocked UUID generation"
        1 * uuidFactory.generate() >> expectedUuid

        when: "Factory creates appointment"
        def appointment = factory.create(slotId, patientId, serviceName, practitionerId, comment, start, end, status)

        then: "All fields are mapped correctly"
        appointment.id == expectedUuid
        appointment.slotId == slotId
        appointment.patientId == patientId
        appointment.practitionerId == practitionerId
        appointment.serviceName == serviceName
        appointment.comment == comment
        appointment.startTime == start
        appointment.endTime == end
        appointment.status == status
        appointment.createdAt != null
        appointment.updatedAt != null
    }

    void "should throw exception when slotId is null"() {
        when:
        factory.create(null, UUID.randomUUID(), "Service", UUID.randomUUID(), "comment", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), AppointmentStatus.SCHEDULED)

        then:
        thrown(NullPointerException)
    }

    void "should throw exception when serviceName is blank"() {
        when:
        factory.create(1L, UUID.randomUUID(), "  ", UUID.randomUUID(), "comment", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), AppointmentStatus.SCHEDULED)

        then:
        thrown(StringValidationException)
    }
}