package dev.tsumakov.appointments.slot.exception;

public class SlotNotFoundException extends RuntimeException {

  public SlotNotFoundException(String message) {
    super(message);
  }
}
