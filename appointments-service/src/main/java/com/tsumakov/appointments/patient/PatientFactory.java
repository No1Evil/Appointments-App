package dev.tsumakov.appointments.patient;

import dev.tsumakov.appointments.common.AppointmentObjects;
import dev.tsumakov.appointments.common.factory.UuidFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class PatientFactory {

  private final UuidFactory uuidFactory;

  public Patient create(String firstName, String lastName) {
    AppointmentObjects.requireNotBlank(firstName, "firstName");
    AppointmentObjects.requireNotBlank(lastName, "lastName");

    return Patient.builder()
        .id(uuidFactory.generate())
        .firstName(firstName)
        .lastName(lastName)
        .build();
  }

}
