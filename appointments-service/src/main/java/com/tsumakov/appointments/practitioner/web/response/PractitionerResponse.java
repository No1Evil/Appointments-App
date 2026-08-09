package dev.tsumakov.appointments.practitioner.web.response;

import dev.tsumakov.appointments.service.web.response.ServiceCategoryResponse;
import io.micronaut.serde.annotation.Serdeable;
import java.util.UUID;

@Serdeable
public record PractitionerResponse(
    UUID id,
    String firstName,
    String lastName,
    ServiceCategoryResponse service
) {

}
