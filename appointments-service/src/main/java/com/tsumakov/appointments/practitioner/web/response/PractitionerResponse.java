package dev.tsumakov.appointments.practitioner.web.response;

import java.util.UUID;

public record PractitionerResponse(
    UUID id,
    String firstName,
    String secondName,
    String serviceName
) {

}
