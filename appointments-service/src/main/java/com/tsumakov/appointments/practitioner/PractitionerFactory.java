package dev.tsumakov.appointments.practitioner;

import dev.tsumakov.appointments.common.AppointmentObjects;
import dev.tsumakov.appointments.common.factory.UuidFactory;
import dev.tsumakov.appointments.service.ServiceCategory;
import jakarta.inject.Singleton;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class PractitionerFactory {

  private final UuidFactory uuidFactory;

  public Practitioner create(String firstName, String lastName, ServiceCategory service) {
    AppointmentObjects.requireNotBlank(firstName, "firstName");
    AppointmentObjects.requireNotBlank(lastName, "lastName");
    Objects.requireNonNull(service, "ServiceCategory cannot be null");

    return Practitioner.builder()
        .id(uuidFactory.generate())
        .firstName(firstName)
        .lastName(lastName)
        .service(service)
        .build();
  }
}
