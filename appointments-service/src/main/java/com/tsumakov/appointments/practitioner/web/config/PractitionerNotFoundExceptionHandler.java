package dev.tsumakov.appointments.practitioner.web.config;

import dev.tsumakov.appointments.common.web.ErrorResponse;
import dev.tsumakov.appointments.practitioner.exception.PractitionerNotFoundException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Produces
public final class PractitionerNotFoundExceptionHandler
  implements ExceptionHandler<PractitionerNotFoundException, HttpResponse<?>> {

  @Override
  public HttpResponse<?> handle(HttpRequest request, PractitionerNotFoundException exception) {
    return HttpResponse.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
  }
}
