package dev.tsumakov.appointments.slot;

import dev.tsumakov.appointments.common.repository.CrudRepository;
import dev.tsumakov.appointments.service.ServiceCategoryService;
import dev.tsumakov.appointments.slot.status.SlotStatus;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SlotRepository implements CrudRepository<Slot, Long> {

  private final JdbcTemplate jdbcTemplate;
  private final ServiceCategoryService serviceCategoryService;

  private final RowMapper<Slot> rowMapper = ((rs, rowNum) -> Slot.builder()
      .id(rs.getLong("id"))
      .status(SlotStatus.valueOf(rs.getString("status")))
      .service(serviceCategoryService.getServiceByCode(rs.getString("service")))
      .startTime(rs.getObject("start_time", OffsetDateTime.class))
      .endTime(rs.getObject("end_time", OffsetDateTime.class))
      .build());

  @Override
  public Optional<Slot> findBy(@NonNull Long identifier) throws DataAccessException {
    String sql = "select * from slots where id = ?";
    var query = jdbcTemplate.query(sql, rowMapper, identifier);
    return query.stream().findFirst();
  }

  @Override
  public List<Slot> findAll() throws DataAccessException {
    String sql = "select * from slots";
    return jdbcTemplate.query(sql, rowMapper);
  }

  @Override
  public Long create(@NonNull Slot entity) throws DataAccessException {
    String sql = """
        insert into slots (
          status,
          service,
          start_time,
          end_time
        ) values (?, ?, ?, ?, ?)
        """;
    int rowsUpdated = jdbcTemplate.update(sql, entity.getStatus().toString(),
        entity.getService().getCode(), entity.getStartTime(), entity.getEndTime());
    return 0L;
  }

  @Override
  public boolean update(@NonNull Slot entity) throws DataAccessException {
    String sql = """
        update slots set
          status = ?,
          service = ?,
          start_time = ?,
          end_time = ?
        where id = ?
        """;
    int rowsUpdated = jdbcTemplate.update(sql, entity.getStatus().toString(),
        entity.getService().getCode(), entity.getStartTime(), entity.getEndTime(), entity.getId());
    return rowsUpdated > 0;
  }

  @Override
  public boolean delete(@NonNull Long identifier) throws DataAccessException {
    String sql = "delete from slots where id = ?";
    int rowsUpdated = jdbcTemplate.update(sql, identifier);
    return rowsUpdated > 0;
  }

  public List<Slot> listByFilter(SlotParams params) {
    String sql = "select * from slot";
    sql += buildFilters(params);
    return jdbcTemplate.query(sql, rowMapper);
  }

  private String buildFilters(SlotParams params) {
    //TODO: build filters here

    return "";
  }

}

