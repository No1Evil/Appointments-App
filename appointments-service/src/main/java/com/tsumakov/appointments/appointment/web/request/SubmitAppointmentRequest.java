package dev.tsumakov.appointments.appointment.web.request;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Serdeable
public record SubmitAppointmentRequest(
    @NotNull(message = "Slot ID is required")
    Long slotId,

    @NotNull(message = "Patient ID is required")
    UUID patientId,

    @NotNull(message = "Practitioner ID is required")
    UUID practitionerId,

    @Size(max = 500, message = "Comment must not exceed 500 characters")
    String comment
) {

}
