package dev.tsumakov.appointments.slot;

import dev.tsumakov.appointments.slot.status.SlotStatus;
import jakarta.annotation.Nullable;
import java.time.OffsetDateTime;

public record SlotParams(
    @Nullable SlotStatus status,
    @Nullable String serviceCode,
    @Nullable OffsetDateTime startTime
) {

}
