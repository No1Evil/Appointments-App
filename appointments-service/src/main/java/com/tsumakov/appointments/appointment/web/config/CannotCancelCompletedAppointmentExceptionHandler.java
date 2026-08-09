package dev.tsumakov.appointments.appointment.web.config;

import dev.tsumakov.appointments.appointment.exception.CannotCancelCompletedAppointmentException;
import dev.tsumakov.appointments.common.web.ErrorResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import org.springframework.stereotype.Component;

@Produces
@Component
public final class CannotCancelCompletedAppointmentExceptionHandler
    implements ExceptionHandler<CannotCancelCompletedAppointmentException, HttpResponse<?>> {

  @Override
  public HttpResponse<?> handle(HttpRequest request,
      CannotCancelCompletedAppointmentException exception) {
    return HttpResponse.status(HttpStatus.CONFLICT)
        .body(new ErrorResponse(exception.getMessage()));
  }
}
