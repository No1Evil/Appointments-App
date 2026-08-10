package dev.tsumakov.appointments.appointment;

import dev.tsumakov.appointments.appointment.exception.AppointmentAlreadyCancelledException;
import dev.tsumakov.appointments.appointment.exception.AppointmentValidationException;
import dev.tsumakov.appointments.appointment.exception.CannotUpdateCompletedAppointmentException;
import dev.tsumakov.appointments.appointment.status.AppointmentStatus;
import dev.tsumakov.appointments.common.AppointmentObjects;
import dev.tsumakov.appointments.slot.Slot;
import jakarta.annotation.Nullable;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Appointment {

  @EqualsAndHashCode.Include
  private UUID id;
  private Long slotId;
  private final UUID patientId;
  private final UUID practitionerId;

  private String practitionerName; // Read only
  private String patientName; // Read only

  private String serviceName; // name of the service from the slot selected
  private OffsetDateTime startTime;
  private OffsetDateTime endTime;
  @Nullable
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
    checkIfUpdatingApplicable();
    this.status = AppointmentStatus.CANCELLED;
    this.updatedAt = OffsetDateTime.now();
  }

  public void updateTimestamp(OffsetDateTime newStart, OffsetDateTime newEnd) {
    checkIfUpdatingApplicable();
    AppointmentObjects.requireValidDates(newStart, newEnd);
    this.startTime = newStart;
    this.endTime = newEnd;
    this.updatedAt = OffsetDateTime.now();
  }

  public void updateSlot(Long slotId) {
    checkIfUpdatingApplicable();
    Objects.requireNonNull(slotId, "Slot id can not be null");
    this.slotId = slotId;
    this.updatedAt = OffsetDateTime.now();
  }

  public void updateComment(@Nullable String comment) {
    checkIfUpdatingApplicable();
    this.comment = comment;
    this.updatedAt = OffsetDateTime.now();
  }

  public void updateService(String serviceName) {
    checkIfUpdatingApplicable();
    AppointmentObjects.requireNotBlank(serviceName, "serviceName");
    this.serviceName = serviceName;
    this.updatedAt = OffsetDateTime.now();
  }

  public void complete() {
    checkIfUpdatingApplicable();
    if (OffsetDateTime.now().isBefore(startTime)) {
      throw new AppointmentValidationException("Cannot complete appointment that is before now");
    }
    this.status = AppointmentStatus.COMPLETED;
    this.updatedAt = OffsetDateTime.now();
  }

  public void reschedule(Slot slot, @Nullable String comment) {
    Objects.requireNonNull(slot, "Slot cannot be null");
    updateSlot(slot.getId());
    updateTimestamp(slot.getStartTime(), slot.getEndTime());
    updateComment(comment);
    updateService(slot.getService().getName());
  }

  private void checkIfUpdatingApplicable() {
    if (this.isCancelled()) {
      throw new AppointmentAlreadyCancelledException("Cannot update cancelled appointment");
    } else if (this.isCompleted()) {
      throw new CannotUpdateCompletedAppointmentException("Cannot update completed appointment");
    }
  }
}
