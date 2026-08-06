package dev.tsumakov.appointments.appointment;

import dev.tsumakov.appointments.appointment.status.AppointmentStatus;
import java.util.UUID;

public record AppointmentsParams(
    UUID practitionerId,
    AppointmentStatus status,
    String serviceName,
    UUID patientId
) {

}
