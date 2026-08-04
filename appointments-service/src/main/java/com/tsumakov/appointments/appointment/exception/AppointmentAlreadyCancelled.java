package dev.tsumakov.appointments.appointment.exception;

public class AppointmentAlreadyCancelled extends RuntimeException {
    public AppointmentAlreadyCancelled(String message) {
        super(message);
    }
}
