package dev.tsumakov.appointments.appointment.exception;

public class CannotUpdateCompletedAppointmentException extends RuntimeException {
    public CannotUpdateCompletedAppointmentException(String message) {
        super(message);
    }
}
