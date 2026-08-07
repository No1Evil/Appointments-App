package dev.tsumakov.appointments.patient.web;

import dev.tsumakov.appointments.patient.PatientService;
import dev.tsumakov.appointments.patient.mapper.PatientMapper;
import dev.tsumakov.appointments.patient.web.response.PatientResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Controller("/patients")
@RequiredArgsConstructor
public class PatientController {

  private final PatientService service;
  private final PatientMapper mapper;

  @Get("/{id}")
  public HttpResponse<PatientResponse> get(@PathVariable UUID id){
    var result = service.findById(id);
    return HttpResponse.ok(mapper.toResponse(result));
  }

  @Get
  public HttpResponse<List<PatientResponse>> getAll() {
    var result = service.listAll();
    var response = result.stream().map(mapper::toResponse).toList();
    return HttpResponse.ok(response);
  }

}
