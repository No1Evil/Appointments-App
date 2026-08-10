package dev.tsumakov.appointments.slot;

import dev.tsumakov.appointments.common.repository.CrudRepository;
import jakarta.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public final class SlotRepository implements CrudRepository<Slot, Long> {

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
          service_code,
          start_time,
          end_time
        ) values (?, ?, ?, ?)
        """;
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"}); // Or Statement.RETURN_GENERATED_KEYS
      ps.setString(1, entity.getStatusName());
      ps.setString(2, entity.getService().getCode());
      ps.setObject(3, entity.getStartTime());
      ps.setObject(4, entity.getEndTime());
      return ps;
    }, keyHolder);

    Number key = keyHolder.getKey();
    return key != null ? key.longValue() : null;
  }

  @Override
  public boolean update(@NonNull Slot entity) throws DataAccessException {
    String sql = """
        update slots set
          status = ?,
          service_code = ?,
          start_time = ?,
          end_time = ?
        where id = ?
        """;
    int rowsUpdated = jdbcTemplate.update(sql, entity.getStatusName(),
        entity.getService().getCode(), entity.getStartTime(), entity.getEndTime(), entity.getId());
    return rowsUpdated > 0;
  }

  @Override
  public boolean delete(@NonNull Long identifier) throws DataAccessException {
    String sql = "delete from slots where id = ?";
    int rowsUpdated = jdbcTemplate.update(sql, identifier);
    return rowsUpdated > 0;
  }

  public List<Slot> listByFilter(@Nonnull SlotParams params) {
    StringBuilder sql = new StringBuilder("select * from slots where 1=1");
    List<Object> args = new ArrayList<>();

    if (params.status() != null) {
      sql.append(" and status = ?");
      args.add(params.status().toString().trim().toLowerCase());
    }
    if (params.serviceCode() != null) {
      sql.append(" and service_code = ?");
      args.add(params.serviceCode());
    }
    if (params.startTime() != null) {
      sql.append(" and start_time >= ?");
      args.add(params.startTime());
    }

    return jdbcTemplate.query(sql.toString(), rowMapper, args.toArray());
  }

}

