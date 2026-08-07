package dev.tsumakov.appointments.common.web;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ErrorResponse(
    String message
) {

}
