package dev.tsumakov.appointments.appointment.web;

import dev.tsumakov.appointments.appointment.AppointmentService;
import dev.tsumakov.appointments.appointment.mapper.AppointmentMapper;
import dev.tsumakov.appointments.appointment.status.AppointmentStatus;
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
import io.micronaut.http.annotation.QueryValue;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Controller("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

  private final AppointmentService service;
  private final AppointmentMapper mapper;

  @Get
  public HttpResponse<List<AppointmentResponse>> get(
      @QueryValue @Nullable UUID practitionerId,
      @QueryValue @Nullable AppointmentStatus status,
      @QueryValue @Nullable String serviceName,
      @QueryValue @Nullable UUID patientId) {
    var request = new FilterAppointmentsRequest(practitionerId, status, serviceName, patientId);
    var result = service.listFiltered(request).stream().map(mapper::toResponse).toList();
    return HttpResponse.ok(result);
  }

  @Post
  public HttpResponse<AppointmentResponse> submitAppointment(
      @Valid @Body SubmitAppointmentRequest request
  ) {
    var result = service.submit(request);
    return HttpResponse.created(mapper.toResponse(result));
  }

  @Put
  public HttpResponse<AppointmentResponse> reschedule(
      @Valid @Body RescheduleAppointmentRequest request
  ) {
    var result = service.reschedule(request);
    return HttpResponse.ok(mapper.toResponse(result));
  }

  @Patch()
  public HttpResponse<AppointmentResponse> updateComment(
      @Body @Valid UpdateAppointmentCommentRequest request
  ) {
    var result = service.updateComment(request);
    return HttpResponse.ok(mapper.toResponse(result));
  }

  @Delete("/{id}")
  public HttpResponse<Void> cancel(@PathVariable UUID id) {
    service.cancel(id);
    return HttpResponse.noContent();
  }
}
