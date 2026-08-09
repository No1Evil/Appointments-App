package dev.tsumakov.appointments.practitioner.web.request;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;

@Serdeable
@Introspected
public record FilterPractitionersRequest(
    @Nullable String serviceCode
) {

}
