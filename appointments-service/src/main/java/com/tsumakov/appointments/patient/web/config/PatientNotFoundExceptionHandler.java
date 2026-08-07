package dev.tsumakov.appointments.patient.web.config;

import dev.tsumakov.appointments.common.web.ErrorResponse;
import dev.tsumakov.appointments.patient.exception.PatientNotFoundException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Produces
public final class PatientNotFoundExceptionHandler
    implements ExceptionHandler<PatientNotFoundException, HttpResponse<?>> {

  @Override
  public HttpResponse<?> handle(HttpRequest request, PatientNotFoundException exception) {
    return HttpResponse.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
  }
}
