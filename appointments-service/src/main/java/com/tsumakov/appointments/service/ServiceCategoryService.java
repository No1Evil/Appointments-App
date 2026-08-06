package dev.tsumakov.appointments.service;

import dev.tsumakov.appointments.service.exception.ServiceCategoryNotFound;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ServiceCategoryService {

  private static final Map<String, ServiceCategory> serviceCategories = Map.of(
      "dental", new ServiceCategory().setCode("dental").setName("Dental Service"),
      "gp", new ServiceCategory().setCode("gp").setName("General Practitioner"),
      "mental-health", new ServiceCategory().setCode("mental-health").setName("Mental Health")
  );

  public List<ServiceCategory> getServices() {
    return serviceCategories.values().stream().toList();
  }

  public ServiceCategory getServiceByCode(String code) {
    var service = serviceCategories.get(code);
    if (service == null) {
      throw new ServiceCategoryNotFound("No such service category with code " + code);
    }
    return service;
  }
}
