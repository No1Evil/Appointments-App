package dev.tsumakov.appointments.patient.web.response;

import io.micronaut.serde.annotation.Serdeable;
import java.util.UUID;

@Serdeable
public record PatientResponse(
    UUID id,
    String firstName,
    String lastName
) {

}
