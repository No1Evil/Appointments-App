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
@ToString
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Appointment {

  @EqualsAndHashCode.Include
  private UUID id;
  private Long slotId;
  private final UUID patientId;
  private final UUID practitionerId;

  private final String serviceName; // name of the service from the slot selected
  private String practitionerName; // Read only
  private String patientName; // Read only

  private OffsetDateTime startTime;
  private OffsetDateTime endTime;
  private String comment;

  private AppointmentStatus status;

  private final OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;

  public boolean isScheduled() {
    return this.status == AppointmentStatus.SCHEDULED;
  }

  public boolean isCancelled() {
    return this.status == AppointmentStatus.CANCELLED;
  }

  public boolean isCompleted() {
    return this.status == AppointmentStatus.COMPLETED;
  }

  public void cancel() {
    if (this.isCancelled()) {
      throw new AppointmentAlreadyCancelled("Appointment is already cancelled");
    } else if (this.isCompleted()) {
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
    this.updatedAt = OffsetDateTime.now();
  }
}
