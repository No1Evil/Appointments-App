package dev.tsumakov.appointments.slot.web.response;

import dev.tsumakov.appointments.service.ServiceCategory;
import dev.tsumakov.appointments.slot.status.SlotStatus;
import io.micronaut.serde.annotation.Serdeable;
import java.time.OffsetDateTime;

@Serdeable
public record SlotResponse(
    Long id,
    SlotStatus status,
    ServiceCategory service,
    OffsetDateTime startTime,
    OffsetDateTime endTime
) {

}
