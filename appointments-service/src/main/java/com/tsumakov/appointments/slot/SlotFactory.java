package dev.tsumakov.appointments.slot;

import dev.tsumakov.appointments.common.AppointmentObjects;
import dev.tsumakov.appointments.service.ServiceCategory;
import dev.tsumakov.appointments.slot.status.SlotStatus;
import jakarta.inject.Singleton;
import java.time.OffsetDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public final class SlotFactory {

  public Slot create(SlotStatus status, ServiceCategory service,
      OffsetDateTime startTime, OffsetDateTime endTime) {
    Objects.requireNonNull(status, "Status cannot be null");
    Objects.requireNonNull(service, "Service cannot be null");
    AppointmentObjects.requireValidDates(startTime, endTime);

    return Slot.builder()
        .id(null)
        .status(status)
        .service(service)
        .startTime(startTime)
        .endTime(endTime)
        .build();
  }
}
