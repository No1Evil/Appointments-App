package dev.tsumakov.appointments.service;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Controller("services")
@RequiredArgsConstructor
public class ServiceCategoryController {

  private final ServiceCategoryService serviceCategoryService;

  @Get
  public List<ServiceCategory> getServices() {
    return serviceCategoryService.getServices();
  }

  @Get("/{code}")
  public ServiceCategory getServiceByCode(@PathVariable String code) {
    return serviceCategoryService.getServiceByCode(code);
  }
}
