package dev.tsumakov.appointments.appointment.web.request;

import dev.tsumakov.appointments.appointment.status.AppointmentStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import java.util.UUID;

@Serdeable
@Introspected
public record FilterAppointmentsRequest(
    @Nullable
    UUID practitionerId,

    @Nullable
    AppointmentStatus status,

    @Nullable
    String serviceName,

    @Nullable
    UUID patientId
) {

}
