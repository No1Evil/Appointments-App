package dev.tsumakov.appointments.practitioner;

import dev.tsumakov.appointments.common.repository.CrudRepository;
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
public class PractitionerRepository implements CrudRepository<Practitioner, UUID> {

  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<Practitioner> rowMapper;

  @Override
  public Optional<Practitioner> findBy(@NonNull UUID identifier) throws DataAccessException {
    String sql = "select * from practitioners where id = ?";
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
}
