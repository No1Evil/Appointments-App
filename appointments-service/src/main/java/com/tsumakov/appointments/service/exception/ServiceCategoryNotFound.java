package dev.tsumakov.appointments.service.exception;

public class ServiceCategoryNotFound extends RuntimeException {

  public ServiceCategoryNotFound(String message) {
    super(message);
  }
}
