package dev.tsumakov.appointments.appointment

import dev.tsumakov.appointments.appointment.status.AppointmentStatus
import spock.lang.Specification
import java.time.OffsetDateTime
import dev.tsumakov.appointments.appointment.exception.AppointmentAlreadyCancelled
import dev.tsumakov.appointments.appointment.exception.CannotCancelCompletedAppointment

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
        thrown(AppointmentAlreadyCancelled)
    }

    void "should throw exception when cancelling completed appointment"() {
        given: "A completed appointment"
        def appointment = createAppointment(AppointmentStatus.COMPLETED)

        when: "We try to cancel it"
        appointment.cancel()

        then: "It throws CannotCancelCompletedAppointment"
        thrown(CannotCancelCompletedAppointment)
    }

    void "should update details successfully"() {
        given: "An appointment"
        def appointment = createAppointment()
        def newStart = OffsetDateTime.now().plusDays(1)
        def newEnd = newStart.plusHours(1)
        def newComment = "New comment"

        when: "We update details"
        appointment.updateDetails(newStart, newEnd, newComment)

        then: "Details are updated"
        appointment.startTime == newStart
        appointment.endTime == newEnd
        appointment.comment == newComment
    }
}
