package dev.tsumakov.appointments.service;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
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

}
