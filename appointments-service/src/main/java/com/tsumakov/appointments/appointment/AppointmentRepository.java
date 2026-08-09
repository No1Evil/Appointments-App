package dev.tsumakov.appointments.appointment;

import dev.tsumakov.appointments.appointment.status.AppointmentStatus;
import dev.tsumakov.appointments.common.repository.CrudRepository;
import jakarta.annotation.Nonnull;
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
      .patientName(rs.getString("patient_name"))
      .practitionerName(rs.getString("practitioner_name"))
      .startTime(rs.getObject("start_time", OffsetDateTime.class))
      .endTime(rs.getObject("end_time", OffsetDateTime.class))
      .comment(rs.getString("comment"))
      .status(AppointmentStatus.valueOf(rs.getString("status")))
      .createdAt(rs.getObject("created_at", OffsetDateTime.class))
      .updatedAt(rs.getObject("updated_at", OffsetDateTime.class))
      .build());

  @Override
  public Optional<Appointment> findBy(@NonNull UUID identifier) throws DataAccessException {
    String sql = """
        select a.*,
          p.first_name || ' ' || p.last_name as practitioner_name,
          pt.first_name || ' ' || pt.last_name as patient_name
        from appointments a
        left join practitioners p on p.id = a.practitioner_id
        left join patients pt on pt.id = a.patient_id
        where a.id = ?
        """;
    var query = jdbcTemplate.query(sql, rowMapper, identifier);
    return query.stream().findFirst();
  }

  @Override
  public List<Appointment> findAll() throws DataAccessException {
    String sql = """
        select a.*,
          p.first_name || ' ' || p.last_name as practitioner_name,
          pt.first_name || ' ' || pt.last_name as patient_name
        from appointments a
        left join practitioners p on p.id = a.practitioner_id
        left join patients pt on pt.id = a.patient_id
        """;
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

  public List<Appointment> findUsersOverlappingAppointments(
      @Nonnull UUID patientId, @Nonnull UUID practitionerId,
      @Nonnull OffsetDateTime startTime, @Nonnull OffsetDateTime endTime,
      @Nonnull UUID currentAppointmentId
  ) {
    String sql = """
        select a.*,
          p.first_name || ' ' || p.last_name as practitioner_name,
          pt.first_name || ' ' || pt.last_name as patient_name
        from appointments a
        left join practitioners p on p.id = a.practitioner_id
        left join patients pt on pt.id = a.patient_id
        where (a.patient_id = ? or a.practitioner_id = ?)
          and (?::uuid is null or a.id != ?)
          and a.status not in ('CANCELLED')
          and a.start_time < ?
          and a.end_time > ?
        """;

    return jdbcTemplate.query(
        sql,
        rowMapper,
        patientId,
        practitionerId,
        currentAppointmentId,
        currentAppointmentId,
        endTime,
        startTime
    );
  }

  public List<Appointment> filterBy(AppointmentsParams params) {
    StringBuilder sql = new StringBuilder("""
        select a.*,
          p.first_name || ' ' || p.last_name as practitioner_name,
          pt.first_name || ' ' || pt.last_name as patient_name
        from appointments a
        left join practitioners p on p.id = a.practitioner_id
        left join patients pt on pt.id = a.patient_id
        where 1=1
        """);
    List<Object> args = new ArrayList<>();

    if (params.practitionerId() != null) {
      sql.append(" and a.practitioner_id = ?");
      args.add(params.practitionerId());
    }
    if (params.status() != null) {
      sql.append(" and a.status = ?");
      args.add(params.status().name());
    }
    if (params.serviceName() != null && !params.serviceName().isBlank()) {
      sql.append(" and a.service_name = ?");
      args.add(params.serviceName());
    }
    if (params.patientId() != null) {
      sql.append(" and a.patient_id = ?");
      args.add(params.patientId());
    }

    return jdbcTemplate.query(sql.toString(), rowMapper, args.toArray());
  }
}
