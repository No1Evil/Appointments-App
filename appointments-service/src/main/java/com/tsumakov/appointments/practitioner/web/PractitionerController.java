package dev.tsumakov.appointments.practitioner.web;

import dev.tsumakov.appointments.practitioner.PractitionerService;
import dev.tsumakov.appointments.practitioner.mapper.PractitionerMapper;
import dev.tsumakov.appointments.practitioner.web.response.PractitionerResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Controller("/practitioners")
@RequiredArgsConstructor
public class PractitionerController {

  private final PractitionerService service;
  private final PractitionerMapper mapper;

  @Get("/{id}")
  public HttpResponse<PractitionerResponse> get(@PathVariable UUID id) {
    var result = service.findById(id);
    return HttpResponse.ok(mapper.toResponse(result));
  }

  @Get
  public HttpResponse<List<PractitionerResponse>> getAll() {
    var result = service.listAll();
    var response = result.stream().map(mapper::toResponse).toList();
    return HttpResponse.ok(response);
  }

}
