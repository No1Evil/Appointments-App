package dev.tsumakov.appointments.service;

import jakarta.inject.Singleton;
import java.util.List;

@Singleton
public class ServiceCategoryService {

  public List<ServiceCategory> getServices() {
    return List.of(new ServiceCategory().setCode("dental").setName("Dental Service"),
        new ServiceCategory().setCode("gp").setName("General Practitioner"),
        new ServiceCategory().setCode("mental-health").setName("Mental Health"));
  }
}
