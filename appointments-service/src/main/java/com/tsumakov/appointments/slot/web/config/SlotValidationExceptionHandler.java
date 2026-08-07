package dev.tsumakov.appointments.slot.web.config;

import dev.tsumakov.appointments.common.web.ErrorResponse;
import dev.tsumakov.appointments.slot.exception.SlotValidationException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Produces
public final class SlotValidationExceptionHandler
    implements ExceptionHandler<SlotValidationException, HttpResponse<?>> {

  @Override
  public HttpResponse<?> handle(HttpRequest request, SlotValidationException exception) {
    return HttpResponse.status(HttpStatus.CONFLICT).body(new ErrorResponse(exception.getMessage()));
  }
}
