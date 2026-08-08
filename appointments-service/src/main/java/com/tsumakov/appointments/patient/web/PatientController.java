package dev.tsumakov.appointments.patient.web;

import dev.tsumakov.appointments.patient.PatientService;
import dev.tsumakov.appointments.patient.mapper.PatientMapper;
import dev.tsumakov.appointments.patient.web.response.PatientResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Tag(name = "patient")
@Controller("/api/patients")
@RequiredArgsConstructor
public class PatientController {

  private final PatientService service;
  private final PatientMapper mapper;

  @Get("/{id}")
  @Operation(operationId = "getPatientById", summary = "get patient by id")
  public HttpResponse<PatientResponse> getById(@PathVariable UUID id){
    var result = service.findById(id);
    return HttpResponse.ok(mapper.toResponse(result));
  }

  @Get
  @Operation(operationId = "getAllPatients", summary = "get all patients")
  public HttpResponse<List<PatientResponse>> getAll() {
    var result = service.listAll();
    var response = result.stream().map(mapper::toResponse).toList();
    return HttpResponse.ok(response);
  }

}
