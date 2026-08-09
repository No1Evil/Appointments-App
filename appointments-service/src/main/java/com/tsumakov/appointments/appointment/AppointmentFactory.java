package dev.tsumakov.appointments.appointment;

import dev.tsumakov.appointments.appointment.status.AppointmentStatus;
import dev.tsumakov.appointments.common.AppointmentObjects;
import dev.tsumakov.appointments.common.factory.UuidFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public final class AppointmentFactory {

    private final UuidFactory uuidFactory;

    public Appointment create(
        Long slotId, UUID patientId, String serviceName, UUID practitionerId, String comment,
        OffsetDateTime startTime, OffsetDateTime endTime, AppointmentStatus status
    ) {
        Objects.requireNonNull(slotId, "Slot id must not be null");
        Objects.requireNonNull(patientId, "Patient id must not be null");
        AppointmentObjects.requireNotBlank(serviceName, "Service name");
        AppointmentObjects.requireValidDates(startTime, endTime);
        Objects.requireNonNull(status, "Status cannot be null");

        UUID appointmentId = uuidFactory.generate();
        OffsetDateTime now = OffsetDateTime.now();

        return Appointment.builder()
            .id(appointmentId)
            .slotId(slotId)
            .patientId(patientId)
            .serviceName(serviceName)
            .practitionerId(practitionerId)
            .startTime(startTime)
            .endTime(endTime)
            .status(status)
            .comment(comment)
            .createdAt(now)
            .updatedAt(now)
            .build();
    }
}
