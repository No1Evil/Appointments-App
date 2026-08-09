package dev.tsumakov.appointments.service;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Serdeable
@Data
@Accessors(chain = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ServiceCategory {
  @EqualsAndHashCode.Include
  private String code;
  private String name;
}
