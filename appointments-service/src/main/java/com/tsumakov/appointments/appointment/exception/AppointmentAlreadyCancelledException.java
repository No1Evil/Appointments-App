package dev.tsumakov.appointments.appointment.exception;

public class AppointmentAlreadyCancelledException extends RuntimeException {
    public AppointmentAlreadyCancelledException(String message) {
        super(message);
    }
}
