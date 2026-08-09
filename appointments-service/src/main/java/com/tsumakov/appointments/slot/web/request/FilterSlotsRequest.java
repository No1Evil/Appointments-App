package dev.tsumakov.appointments.slot.web.request;

import dev.tsumakov.appointments.slot.status.SlotStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.convert.format.Format;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import java.time.OffsetDateTime;

@Serdeable
@Introspected
public record FilterSlotsRequest(
    @Nullable SlotStatus status,
    @Nullable String serviceCode,
    @Nullable @Format("yyyy-MM-dd'T'HH:mm:ss.SSSX") OffsetDateTime startTime
) {

}
