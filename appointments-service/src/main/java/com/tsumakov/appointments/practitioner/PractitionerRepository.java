package dev.tsumakov.appointments.practitioner;

import dev.tsumakov.appointments.common.repository.CrudRepository;
import dev.tsumakov.appointments.slot.SlotParams;
import jakarta.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public final class PractitionerRepository implements CrudRepository<Practitioner, UUID> {

  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<Practitioner> rowMapper;

  @Override
  public Optional<Practitioner> findById(@NonNull UUID identifier) throws DataAccessException {
    String sql = "select * from practitioners where id = ?";
    var query = jdbcTemplate.query(sql, rowMapper, identifier);
    return query.stream().findFirst();
  }

  @Override
  public Optional<Practitioner> findByIdLocking(@NonNull UUID identifier)
      throws DataAccessException {
    String sql = "select * from practitioners where id = ? for update";
    var query = jdbcTemplate.query(sql, rowMapper, identifier);
    return query.stream().findFirst();
  }

  @Override
  public List<Practitioner> findAll() throws DataAccessException {
    String sql = "select * from practitioners";
    return jdbcTemplate.query(sql, rowMapper);
  }

  @Override
  public UUID create(@NonNull Practitioner entity) throws DataAccessException {
    String sql = """
        insert into practitioners(
        id,
        first_name,
        last_name,
        service_code
        ) values (?, ?, ?, ?)
        """;
    jdbcTemplate.update(sql, entity.getId(), entity.getFirstName(), entity.getLastName(),
        entity.getService().getCode());
    return entity.getId();
  }

  @Override
  public boolean update(@NonNull Practitioner entity) throws DataAccessException {
    String sql = """
        update practitioners set
          first_name = ?,
          last_name = ?,
          service_code = ?
        where id = ?
        """;
    int rowsUpdated = jdbcTemplate.update(sql, entity.getFirstName(), entity.getLastName(),
        entity.getService().getCode(), entity.getId());
    return rowsUpdated > 0;
  }

  @Override
  public boolean delete(@NonNull UUID identifier) throws DataAccessException {
    String sql = "delete from practitioners where id = ?";
    int rowsUpdated = jdbcTemplate.update(sql, identifier);
    return rowsUpdated > 0;
  }

  public List<Practitioner> listByFilter(@Nonnull PractitionerParams params) {
    StringBuilder sql = new StringBuilder("select * from practitioners where 1=1");
    List<Object> args = new ArrayList<>();

    if (params.serviceCode() != null) {
      sql.append(" and service_code = ?");
      args.add(params.serviceCode());
    }

    return jdbcTemplate.query(sql.toString(), rowMapper, args.toArray());
  }
}
