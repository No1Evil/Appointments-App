package dev.tsumakov.appointments.appointment.exception;

public class AppointmentValidationException extends RuntimeException {

  public AppointmentValidationException(String message) {
    super(message);
  }
}
