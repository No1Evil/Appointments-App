package dev.tsumakov.appointments.slot;

import dev.tsumakov.appointments.slot.status.SlotStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.convert.format.Format;
import jakarta.annotation.Nullable;
import java.time.OffsetDateTime;

@Introspected
public record SlotParams(
    @Nullable SlotStatus status,
    @Nullable String serviceCode,
    @Nullable @Format("yyyy-MM-dd'T'HH:mm:ss.SSSX") OffsetDateTime startTime
) {

}
