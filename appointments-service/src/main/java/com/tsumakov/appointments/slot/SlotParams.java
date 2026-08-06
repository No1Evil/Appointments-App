package dev.tsumakov.appointments.slot;

import dev.tsumakov.appointments.slot.status.SlotStatus;
import java.time.OffsetDateTime;

public record SlotParams(
    SlotStatus status,
    String serviceCode,
    OffsetDateTime startTime
) {

}
