package dev.tsumakov.appointments.patient;

import dev.tsumakov.appointments.common.factory.UuidFactory;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class PatientFactory {

  private final UuidFactory uuidFactory;

  public Patient create(String firstName, String lastName) {
    return Patient.builder()
        .id(uuidFactory.generate())
        .firstName(firstName)
        .lastName(lastName)
        .build();
  }

}
