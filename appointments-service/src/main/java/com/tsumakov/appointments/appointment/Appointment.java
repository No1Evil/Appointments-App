package dev.tsumakov.appointments.appointment;

import dev.tsumakov.appointments.appointment.exception.AppointmentAlreadyCancelled;
import dev.tsumakov.appointments.appointment.exception.CannotCancelCompletedAppointment;
import dev.tsumakov.appointments.appointment.status.AppointmentStatus;
import dev.tsumakov.appointments.common.AppointmentObjects;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor
public class Appointment {
    private UUID id;
    private UUID slotId;
    private final UUID patientId;
    private final UUID practitionerId;

    private final String serviceName; // name of the service from the slot selected
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private String comment;

    private AppointmentStatus status;

    private final OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public void cancel() {
        if (this.status == AppointmentStatus.CANCELLED) {
            throw new AppointmentAlreadyCancelled("Appointment is already cancelled");
        } else if (this.status == AppointmentStatus.COMPLETED) {
            throw new CannotCancelCompletedAppointment("Cannot cancel a completed appointment");
        }
        this.status = AppointmentStatus.CANCELLED;
        this.updatedAt = OffsetDateTime.now();
    }

    public void updateDetails(OffsetDateTime newStart, OffsetDateTime newEnd, String newComment) {
        AppointmentObjects.requireValidDates(newStart, newEnd);
        Objects.requireNonNull(newComment);
        this.startTime = newStart;
        this.endTime = newEnd;
        this.comment = newComment;
    }
}
