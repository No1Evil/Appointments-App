package dev.tsumakov.appointments.practitioner;

import dev.tsumakov.appointments.service.ServiceCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Practitioner {
  @EqualsAndHashCode.Include
  private UUID id;
  private String firstName;
  private String lastName;
  private ServiceCategory service;
}
