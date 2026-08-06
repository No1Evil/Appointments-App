package dev.tsumakov.appointments.appointment.web.response;

import dev.tsumakov.appointments.appointment.status.AppointmentStatus;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.OffsetDateTime;
import java.util.UUID;

@Serdeable
public record AppointmentResponse(
    @Nonnull UUID id,
    @Nonnull String serviceName,
    @Nonnull String patientName,
    @Nonnull String practitionerName,
    @Nonnull OffsetDateTime startTime,
    @Nonnull OffsetDateTime endTime,
    @Nullable String comment,
    @Nonnull AppointmentStatus status
) {
}
