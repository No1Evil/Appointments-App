package dev.tsumakov.appointments.service.web;

import dev.tsumakov.appointments.service.ServiceCategoryService;
import dev.tsumakov.appointments.service.mapper.ServiceCategoryMapper;
import dev.tsumakov.appointments.service.web.response.ServiceCategoryResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Tag(name = "service_category")
@Controller("/api/services")
@RequiredArgsConstructor
public class ServiceCategoryController {

  private final ServiceCategoryService serviceCategoryService;
  private final ServiceCategoryMapper mapper;

  @Get
  @Operation(operationId = "getAllServices", summary = "get services")
  public HttpResponse<List<ServiceCategoryResponse>> getAll() {
    var result = serviceCategoryService.getServices();
    var response = result.stream().map(mapper::toResponse).toList();
    return HttpResponse.ok(response);
  }

  @Get("/{code}")
  @Operation(operationId = "getServiceByCode", summary = "get service by specific code")
  public HttpResponse<ServiceCategoryResponse> getByCode(@PathVariable String code) {
    var result = serviceCategoryService.getServiceByCode(code);
    return HttpResponse.ok(mapper.toResponse(result));
  }
}
