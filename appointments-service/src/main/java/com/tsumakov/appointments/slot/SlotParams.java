package dev.tsumakov.appointments.slot;

import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class SlotParams {
  private String status;
  private String service;
  private OffsetDateTime time;
}
