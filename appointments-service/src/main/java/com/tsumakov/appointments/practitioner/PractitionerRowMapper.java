package dev.tsumakov.appointments.practitioner;

import dev.tsumakov.appointments.service.ServiceCategoryService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class PractitionerRowMapper implements RowMapper<Practitioner> {

  private final ServiceCategoryService service;

  @Nullable
  @Override
  public Practitioner mapRow(ResultSet rs, int rowNum) throws SQLException {
    return Practitioner.builder()
        .id(rs.getObject("id", UUID.class))
        .firstName(rs.getString("first_name"))
        .lastName(rs.getString("last_name"))
        .service(service.getServiceByCode(rs.getString("service_code")))
        .build();
  }
}
