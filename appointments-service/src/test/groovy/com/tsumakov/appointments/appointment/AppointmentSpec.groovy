package dev.tsumakov.appointments.appointment

import dev.tsumakov.appointments.appointment.status.AppointmentStatus
import dev.tsumakov.appointments.common.exception.StringValidationException
import spock.lang.Specification
import java.time.OffsetDateTime
import dev.tsumakov.appointments.appointment.exception.AppointmentAlreadyCancelledException
import dev.tsumakov.appointments.appointment.exception.CannotUpdateCompletedAppointmentException

class AppointmentSpec extends Specification {

    static def createAppointment(AppointmentStatus status = AppointmentStatus.SCHEDULED) {
        return Appointment.builder()
                .id(UUID.randomUUID())
                .patientId(UUID.randomUUID())
                .practitionerId(UUID.randomUUID())
                .serviceName("Consultation")
                .status(status)
                .startTime(OffsetDateTime.now())
                .endTime(OffsetDateTime.now().plusHours(1))
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build()
    }

    void "should cancel scheduled appointment"() {
        given: "A scheduled appointment"
        def appointment = createAppointment(AppointmentStatus.SCHEDULED)
        def oldUpdateDate = appointment.updatedAt

        when: "We cancel it"
        appointment.cancel()

        then: "Status becomes CANCELLED and updatedAt is updated"
        appointment.status == AppointmentStatus.CANCELLED
        appointment.updatedAt > oldUpdateDate
    }

    void "should throw exception when cancelling already cancelled appointment"() {
        given: "An already cancelled appointment"
        def appointment = createAppointment(AppointmentStatus.CANCELLED)

        when: "We try to cancel it again"
        appointment.cancel()

        then: "It throws AppointmentAlreadyCancelled"
        thrown(AppointmentAlreadyCancelledException)
    }

    void "should throw exception when cancelling completed appointment"() {
        given: "A completed appointment"
        def appointment = createAppointment(AppointmentStatus.COMPLETED)

        when: "We try to cancel it"
        appointment.cancel()

        then: "It throws CannotCancelCompletedAppointment"
        thrown(CannotUpdateCompletedAppointmentException)
    }

    void "should update comment successfully"() {
        given: "An appointment"
        def appointment = createAppointment()
        def newComment = "New comment"

        when: "We update comment"
        appointment.updateComment(newComment)

        then: "Comment is updated"
        appointment.comment == newComment
    }

    void "should throw exception when updating comment of cancelled appointment"() {
        given: "An already cancelled appointment"
        def appointment = createAppointment(AppointmentStatus.CANCELLED)

        when: "We update comment"
        appointment.cancel()

        then: "It throws AppointmentAlreadyCancelled"
        thrown(AppointmentAlreadyCancelledException)
    }

    void "should throw exception when updating comment of completed appointment"() {
        given: "An already cancelled appointment"
        def appointment = createAppointment(AppointmentStatus.COMPLETED)

        when: "We update comment"
        appointment.cancel()

        then: "It throws CannotUpdateCompletedAppointmentException"
        thrown(CannotUpdateCompletedAppointmentException)
    }

    void "should update service successfully"() {
        given: "an appointment"
        def appointment = createAppointment();
        def newServiceName = "New service"

        when: "We update service name"
        appointment.updateService(newServiceName)

        then: "Service name is updated"
        appointment.serviceName == newServiceName
    }

    void "should throw exception when updating application with a service name that is empty"() {
        given: "an appointment"
        def appointment = createAppointment();
        def newServiceName = ""

        when: "We update service name"
        appointment.updateService(newServiceName)

        then: "It throws StringValidationException"
        thrown(StringValidationException)
    }

}
