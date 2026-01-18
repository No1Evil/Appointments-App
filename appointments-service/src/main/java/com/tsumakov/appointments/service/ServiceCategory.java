package dev.tsumakov.appointments.service;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.experimental.Accessors;

@Serdeable
@Data
@Accessors(chain = true)
public class ServiceCategory {
  private String code;
  private String name;
}
