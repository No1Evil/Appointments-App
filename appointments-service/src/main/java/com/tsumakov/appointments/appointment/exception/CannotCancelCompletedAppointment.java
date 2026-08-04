package dev.tsumakov.appointments.appointment.exception;

public class CannotCancelCompletedAppointment extends RuntimeException {
    public CannotCancelCompletedAppointment(String message) {
        super(message);
    }
}
