package dev.tsumakov.appointments.slot;

import io.micronaut.serde.annotation.Serdeable;
import java.time.OffsetDateTime;
import lombok.Data;

@Serdeable
@Data
public class Slot {
  private Long id;
  private String status;
  private String service;
  private OffsetDateTime startTime;
  private OffsetDateTime endTime;
}
