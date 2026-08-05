package dev.tsumakov.appointments.appointment;

import dev.tsumakov.appointments.appointment.status.AppointmentStatus;
import dev.tsumakov.appointments.common.repository.CrudRepository;
import jakarta.annotation.Nullable;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public final class AppointmentRepository implements CrudRepository<Appointment, UUID> {

  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<Appointment> rowMapper = ((rs, rowNum) -> Appointment.builder()
      .id(rs.getObject("id", UUID.class))
      .slotId(rs.getLong("slot_id"))
      .patientId(rs.getObject("patient_id", UUID.class))
      .practitionerId(rs.getObject("practitioner_id", UUID.class))
      .serviceName(rs.getString("service_name"))
      .startTime(rs.getObject("start_time", OffsetDateTime.class))
      .endTime(rs.getObject("end_time", OffsetDateTime.class))
      .comment(rs.getString("comment"))
      .status(AppointmentStatus.valueOf(rs.getString("status")))
      .createdAt(rs.getObject("created_at", OffsetDateTime.class))
      .updatedAt(rs.getObject("updated_at", OffsetDateTime.class))
      .build());

  @Override
  public Optional<Appointment> findBy(@NonNull UUID identifier) throws DataAccessException {
    String sql = "select * from appointments where id = ?";
    var query = jdbcTemplate.query(sql, rowMapper, identifier);
    return query.stream().findFirst();
  }

  @Override
  public List<Appointment> findAll() throws DataAccessException {
    String sql = "select * from appointments";
    return jdbcTemplate.query(sql, rowMapper);
  }

  @Override
  public UUID create(@NonNull Appointment entity) throws DataAccessException {
    String sql = """
        insert into appointments (
            id,
            slot_id,
            patient_id,
            service_name,
            practitioner_id,
            start_time,
            end_time,
            comment,
            status,
            created_at,
            updated_at
        ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    jdbcTemplate.update(
        sql,
        entity.getId(),
        entity.getSlotId(),
        entity.getPatientId(),
        entity.getServiceName(),
        entity.getPractitionerId(),
        entity.getStartTime(),
        entity.getEndTime(),
        entity.getComment(),
        entity.getStatus() != null ? entity.getStatus().name() : null,
        entity.getCreatedAt(),
        entity.getUpdatedAt()
    );

    return entity.getId();
  }

  @Override
  public boolean update(@NonNull Appointment entity) throws DataAccessException {
    String sql = """
        update appointments set
            slot_id = ?,
            patient_id = ?,
            service_name = ?,
            practitioner_id = ?,
            start_time = ?,
            end_time = ?,
            comment = ?,
            status = ?,
            updated_at = ?
        where id = ?
        """;

    int updatedRows = jdbcTemplate.update(
        sql,
        entity.getSlotId(),
        entity.getPatientId(),
        entity.getServiceName(),
        entity.getPractitionerId(),
        entity.getStartTime(),
        entity.getEndTime(),
        entity.getComment(),
        entity.getStatus() != null ? entity.getStatus().name() : null,
        entity.getUpdatedAt(),
        entity.getId()
    );

    return updatedRows > 0;
  }

  @Override
  public boolean delete(@NonNull UUID identifier) throws DataAccessException {
    String sql = "delete from appointments where id = ?";
    int updatedRows = jdbcTemplate.update(sql, identifier);
    return updatedRows > 0;
  }

  public List<Appointment> filterBy(
      @Nullable UUID practitionerId,
      @Nullable AppointmentStatus status,
      @Nullable String serviceName,
      @Nullable UUID patientId
  ) {
    StringBuilder sql = new StringBuilder("SELECT * FROM appointments WHERE 1=1");
    List<Object> params = new ArrayList<>();

    if (practitionerId != null) {
      sql.append(" AND practitioner_id = ?");
      params.add(practitionerId);
    }
    if (status != null) {
      sql.append(" AND status = ?");
      params.add(status.name());
    }
    if (serviceName != null && !serviceName.isBlank()) {
      sql.append(" AND service_name = ?");
      params.add(serviceName);
    }
    if (patientId != null) {
      sql.append(" AND patient_id = ?");
      params.add(patientId);
    }

    return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
  }
}
