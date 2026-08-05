package dev.tsumakov.appointments.practitioner.exception;

public class PractitionerNotFoundException extends RuntimeException {

  public PractitionerNotFoundException(String message) {
    super(message);
  }
}
