package dev.tsumakov.appointments.patient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor
public class Patient {

  private UUID id;
  private String firstName;
  private String lastName;

}
