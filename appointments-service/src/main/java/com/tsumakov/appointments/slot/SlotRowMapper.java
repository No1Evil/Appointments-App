package dev.tsumakov.appointments.slot;

import dev.tsumakov.appointments.service.ServiceCategoryService;
import dev.tsumakov.appointments.slot.status.SlotStatus;
import jakarta.inject.Singleton;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

@Singleton
@RequiredArgsConstructor
public class SlotRowMapper implements RowMapper<Slot> {

  private final ServiceCategoryService service;

  @Nullable
  @Override
  public Slot mapRow(ResultSet rs, int rowNum) throws SQLException {
    return Slot.builder()
        .id(rs.getLong("id"))
        .status(SlotStatus.valueOf(rs.getString("status").trim().toUpperCase()))
        .service(service.getServiceByCode(rs.getString("service")))
        .startTime(rs.getObject("start_time", OffsetDateTime.class))
        .endTime(rs.getObject("end_time", OffsetDateTime.class))
        .build();
  }
}
