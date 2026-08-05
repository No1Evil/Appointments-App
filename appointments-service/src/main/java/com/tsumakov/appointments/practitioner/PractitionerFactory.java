package dev.tsumakov.appointments.practitioner;

import dev.tsumakov.appointments.common.factory.UuidFactory;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class PractitionerFactory {

  private final UuidFactory uuidFactory;

  public Practitioner create(String firstName, String lastName, String specialty) {
    return Practitioner.builder()
        .id(uuidFactory.generate())
        .firstName(firstName)
        .lastName(lastName)
        .specialty(specialty)
        .build();
  }
}
