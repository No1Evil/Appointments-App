package dev.tsumakov.appointments.slot;

import dev.tsumakov.appointments.service.ServiceCategory;
import dev.tsumakov.appointments.slot.status.SlotStatus;
import jakarta.inject.Singleton;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class SlotFactory {

  public Slot create(SlotStatus status, ServiceCategory service,
      OffsetDateTime startTime, OffsetDateTime endTime) {
    return Slot.builder()
        .id(null)
        .status(status)
        .service(service)
        .startTime(startTime)
        .endTime(endTime)
        .build();
  }
}
