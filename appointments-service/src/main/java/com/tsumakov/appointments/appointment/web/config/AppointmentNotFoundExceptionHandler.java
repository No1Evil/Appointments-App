package dev.tsumakov.appointments.appointment.web.config;

import dev.tsumakov.appointments.appointment.exception.AppointmentNotFoundException;
import dev.tsumakov.appointments.common.web.ErrorResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
public final class AppointmentNotFoundExceptionHandler
    implements ExceptionHandler<AppointmentNotFoundException, HttpResponse<?>> {

  @Override
  public HttpResponse<?> handle(HttpRequest request, AppointmentNotFoundException exception) {
    return HttpResponse.status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(exception.getMessage()));
  }


}
