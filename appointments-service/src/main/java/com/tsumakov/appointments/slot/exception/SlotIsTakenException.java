package dev.tsumakov.appointments.slot.exception;

public class SlotIsTakenException extends RuntimeException {

  public SlotIsTakenException(String message) {
    super(message);
  }
}
