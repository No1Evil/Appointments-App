package dev.tsumakov.appointments.slot;

import dev.tsumakov.appointments.common.repository.CrudRepository;
import jakarta.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SlotRepository implements CrudRepository<Slot, Long> {

  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<Slot> rowMapper;

  @Override
  public Optional<Slot> findBy(@NonNull Long identifier) throws DataAccessException {
    String sql = "select * from slots where id = ?";
    var query = jdbcTemplate.query(sql, rowMapper, identifier);
    return query.stream().findFirst();
  }

  public Optional<Slot> findByWithLock(@Nonnull Long identifier) throws DataAccessException {
    String sql = "select * from slots where id = ? for update";
    var query = jdbcTemplate.query(sql, rowMapper, identifier);
    return query.stream().findFirst();
  }

  @Override
  public List<Slot> findAll() throws DataAccessException {
    String sql = "select * from slots";
    return jdbcTemplate.query(sql, rowMapper);
  }

  @Override
  public Long create(@NonNull Slot entity) throws DataAccessException, NullPointerException {
    String sql = """
        insert into slots (
          status,
          service,
          start_time,
          end_time
        ) values (?, ?, ?, ?)
        """;
    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(sql, keyHolder,
        entity.getStatus().toString(), entity.getService().getCode(),
        entity.getStartTime(), entity.getEndTime());

    return keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
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
    StringBuilder sql = new StringBuilder("select * from slots where 1=1");
    List<Object> args = new ArrayList<>();

    if (params.status() != null) {
      sql.append(" and status = ?");
      args.add(params.status().toString());
    }
    if (params.serviceCode() != null) {
      sql.append(" and service = ?");
      args.add(params.serviceCode());
    }
    if (params.startTime() != null) {
      sql.append(" and start_time >= ?");
      args.add(params.startTime());
    }

    return jdbcTemplate.query(sql.toString(), rowMapper, args.toArray());
  }

}

