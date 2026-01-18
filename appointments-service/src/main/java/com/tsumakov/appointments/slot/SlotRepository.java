package dev.tsumakov.appointments.slot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SlotRepository {
  private final JdbcTemplate jdbcTemplate;

  public Slot load(Long id) {
    String sql = "select * from slot where id = ?";

    return jdbcTemplate.queryForObject(sql, new SlotRowMapper(), id);
  }

  public List<Slot> getSlots(SlotParams params) {
    String sql = "select * from slot";
    sql += buildFilters(params);

    return jdbcTemplate.query(sql, new SlotRowMapper());
  }

  private String buildFilters(SlotParams params) {
    //TODO: build filters here

    return "";
  }

  private static class SlotRowMapper implements RowMapper<Slot> {
    @Override
    public Slot mapRow(ResultSet rs, int rowNum) throws SQLException {
      Slot slot = new Slot();
      slot.setId(rs.getLong("id"));
      slot.setStatus(rs.getString("status"));
      slot.setService(rs.getString("service"));
      slot.setStartTime(rs.getObject("start_time", OffsetDateTime.class));
      slot.setEndTime(rs.getObject("end_time", OffsetDateTime.class));

      return slot;
    }
  }

}

