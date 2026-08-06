package dev.tsumakov.appointments.appointment.web.request;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Serdeable
public record UpdateAppointmentCommentRequest(
    @NotNull(message = "Appointment ID is required")
    UUID appointmentId,

    @Size(max = 500, message = "Comment must not exceed 500 characters")
    String comment
) {

}
