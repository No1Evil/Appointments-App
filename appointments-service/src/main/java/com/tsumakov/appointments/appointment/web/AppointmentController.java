package dev.tsumakov.appointments.appointment.web;

import dev.tsumakov.appointments.appointment.AppointmentService;
import dev.tsumakov.appointments.appointment.mapper.AppointmentMapper;
import dev.tsumakov.appointments.appointment.web.request.FilterAppointmentsRequest;
import dev.tsumakov.appointments.appointment.web.request.RescheduleAppointmentRequest;
import dev.tsumakov.appointments.appointment.web.request.SubmitAppointmentRequest;
import dev.tsumakov.appointments.appointment.web.request.UpdateAppointmentCommentRequest;
import dev.tsumakov.appointments.appointment.web.response.AppointmentResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Tag(name = "appointment")
@Controller("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

  private final AppointmentService service;
  private final AppointmentMapper mapper;

  @Get("/{id}")
  @Operation(operationId = "getById", summary = "gets appointment")
  public HttpResponse<AppointmentResponse> getById(@PathVariable UUID id) {
    var result = service.getAppointment(id);
    return HttpResponse.ok(mapper.toResponse(result));
  }

  @Get("{?request*}")
  @Operation(operationId = "getByFilter", summary = "gets appointment by filter")
  public HttpResponse<List<AppointmentResponse>> getByFilter(@Nullable FilterAppointmentsRequest request) {
    var result = service.listFiltered(request).stream().map(mapper::toResponse).toList();
    return HttpResponse.ok(result);
  }

  @Post
  @Operation(operationId = "submitAppointment", summary = "submits appointment")
  public HttpResponse<AppointmentResponse> submitAppointment(
      @Valid @Body SubmitAppointmentRequest request
  ) {
    var result = service.submit(request);
    return HttpResponse.created(mapper.toResponse(result));
  }

  @Put
  @Operation(operationId = "reschedule", summary = "reschedules appointment")
  public HttpResponse<AppointmentResponse> reschedule(
      @Valid @Body RescheduleAppointmentRequest request
  ) {
    var result = service.reschedule(request);
    return HttpResponse.ok(mapper.toResponse(result));
  }

  @Patch()
  @Operation(operationId = "updateComment", summary = "updates comment on an appointment")
  public HttpResponse<AppointmentResponse> updateComment(
      @Body @Valid UpdateAppointmentCommentRequest request
  ) {
    var result = service.updateComment(request);
    return HttpResponse.ok(mapper.toResponse(result));
  }

  @Delete("/{id}")
  @Operation(operationId = "cancel", summary = "cancels the appointment")
  public HttpResponse<Void> cancel(@PathVariable UUID id) {
    service.cancel(id);
    return HttpResponse.noContent();
  }
}
