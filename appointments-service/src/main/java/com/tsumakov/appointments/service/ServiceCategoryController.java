package dev.tsumakov.appointments.service;

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

  @Get
  @Operation(operationId = "getAll", summary = "get services")
  public HttpResponse<List<ServiceCategory>> getAll() {
    return HttpResponse.ok(serviceCategoryService.getServices());
  }

  @Get("/{code}")
  @Operation(operationId = "getByCode", summary = "get service by specific code")
  public HttpResponse<ServiceCategory> getByCode(@PathVariable String code) {
    return HttpResponse.ok(serviceCategoryService.getServiceByCode(code));
  }
}
