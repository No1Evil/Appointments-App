package dev.tsumakov.appointments.slot.web.config;

import dev.tsumakov.appointments.common.web.ErrorResponse;
import dev.tsumakov.appointments.slot.exception.SlotNotFoundException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Produces
public final class SlotNotFoundExceptionHandler
    implements ExceptionHandler<SlotNotFoundException, HttpResponse<?>> {

  @Override
  public HttpResponse<?> handle(HttpRequest request, SlotNotFoundException exception) {
    return HttpResponse.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
  }
}
