package dev.tsumakov.appointments.appointment.web.config;

import dev.tsumakov.appointments.appointment.exception.AppointmentValidationException;
import dev.tsumakov.appointments.common.web.ErrorResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import org.springframework.stereotype.Component;

@Produces
@Component
public final class AppointmentValidationExceptionHandler
    implements ExceptionHandler<AppointmentValidationException, HttpResponse<?>> {

  @Override
  public HttpResponse<?> handle(HttpRequest request, AppointmentValidationException exception) {
    return HttpResponse.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(exception.getMessage()));
  }
}
