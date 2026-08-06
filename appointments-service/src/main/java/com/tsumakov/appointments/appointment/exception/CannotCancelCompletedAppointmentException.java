package dev.tsumakov.appointments.appointment.exception;

public class CannotCancelCompletedAppointmentException extends RuntimeException {
    public CannotCancelCompletedAppointmentException(String message) {
        super(message);
    }
}
