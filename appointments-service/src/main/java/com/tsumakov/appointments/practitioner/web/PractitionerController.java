package dev.tsumakov.appointments.practitioner.web;

import dev.tsumakov.appointments.practitioner.PractitionerService;
import dev.tsumakov.appointments.practitioner.mapper.PractitionerMapper;
import dev.tsumakov.appointments.practitioner.web.request.FilterPractitionersRequest;
import dev.tsumakov.appointments.practitioner.web.response.PractitionerResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.RequestBean;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Tag(name = "practitioner")
@Controller("/api/practitioners")
@RequiredArgsConstructor
public class PractitionerController {

  private final PractitionerService service;
  private final PractitionerMapper mapper;

  @Get("/{id}")
  @Operation(operationId = "getPractitionerById", summary = "get practitioner by id")
  public HttpResponse<PractitionerResponse> getById(@PathVariable UUID id) {
    var result = service.findById(id);
    return HttpResponse.ok(mapper.toResponse(result));
  }

  @Get
  @Operation(operationId = "getPractitioners", summary = "get all practitioners")
  public HttpResponse<List<PractitionerResponse>> get(
      @Valid @Nullable @RequestBean FilterPractitionersRequest request
  ) {
    var result = service.listByFilter(mapper.toParams(request));
    var response = result.stream().map(mapper::toResponse).toList();
    return HttpResponse.ok(response);
  }

}
