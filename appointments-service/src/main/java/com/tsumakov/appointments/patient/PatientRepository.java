package dev.tsumakov.appointments.patient;

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
public final class PatientRepository implements CrudRepository<Patient, UUID> {

  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<Patient> rowMapper = ((rs, rowNum) -> Patient.builder()
      .id(rs.getObject("id", UUID.class))
      .firstName(rs.getString("first_name"))
      .lastName(rs.getString("last_name"))
      .build());

  @Override
  public Optional<Patient> findBy(@NonNull UUID identifier) throws DataAccessException {
    String sql = "select * from patients where id = ?";
    var query = jdbcTemplate.query(sql, rowMapper, identifier);
    return query.stream().findFirst();
  }

  @Override
  public List<Patient> findAll() throws DataAccessException {
    String sql = "select * from patients";
    return jdbcTemplate.query(sql, rowMapper);
  }

  @Override
  public UUID create(@NonNull Patient entity) throws DataAccessException {
    String sql = """
        insert into patients (
          id,
          first_name,
          last_name
        ) values (?, ?, ?)
        """;
    jdbcTemplate.update(sql, entity.getId(), entity.getFirstName(), entity.getLastName());
    return entity.getId();
  }

  @Override
  public boolean update(@NonNull Patient entity) throws DataAccessException {
    String sql = """
        update patients set
          first_name = ?,
          last_name = ?
        where id = ?
        """;
    int rowsUpdated = jdbcTemplate.update(sql, entity.getFirstName(), entity.getLastName(), entity.getId());
    return rowsUpdated > 0;
  }

  @Override
  public boolean delete(@NonNull UUID identifier) throws DataAccessException {
    String sql = "delete from patients where id = ?";
    int rowsUpdated = jdbcTemplate.update(sql, identifier);
    return rowsUpdated > 0;
  }
}
