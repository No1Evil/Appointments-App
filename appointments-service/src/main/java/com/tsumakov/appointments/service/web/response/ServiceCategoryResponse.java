package dev.tsumakov.appointments.service.web.response;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Serdeable
public record ServiceCategoryResponse(
    @NotNull String code,
    @NotBlank String name
) {

}
