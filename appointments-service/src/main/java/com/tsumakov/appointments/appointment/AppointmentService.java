package dev.tsumakov.appointments.appointment;

import dev.tsumakov.appointments.appointment.exception.AppointmentNotFoundException;
import dev.tsumakov.appointments.appointment.exception.AppointmentValidationException;
import dev.tsumakov.appointments.appointment.mapper.AppointmentMapper;
import dev.tsumakov.appointments.appointment.status.AppointmentStatus;
import dev.tsumakov.appointments.appointment.web.request.FilterAppointmentsRequest;
import dev.tsumakov.appointments.appointment.web.request.SubmitAppointmentRequest;
import dev.tsumakov.appointments.appointment.web.request.RescheduleAppointmentRequest;
import dev.tsumakov.appointments.appointment.web.request.UpdateAppointmentCommentRequest;
import dev.tsumakov.appointments.patient.PatientService;
import dev.tsumakov.appointments.practitioner.PractitionerService;
import dev.tsumakov.appointments.slot.Slot;
import dev.tsumakov.appointments.slot.SlotService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

  private final AppointmentFactory factory;
  private final AppointmentRepository repository;
  private final AppointmentMapper mapper;

  private final SlotService slotService;
  private final PatientService patientService;
  private final PractitionerService practitionerService;

  public Appointment getAppointment(UUID appointmentId) throws AppointmentNotFoundException {
    return repository.findBy(appointmentId).orElseThrow(
        () -> new AppointmentNotFoundException(
            "Appointment with id " + appointmentId + "not found"));
  }

  public List<Appointment> listFiltered(@Nullable FilterAppointmentsRequest request) {
    if (request == null) {
      return repository.findAll();
    }
    var dto = mapper.toDomain(request);
    return repository.filterBy(dto);
  }

  @Transactional
  public void cancel(UUID appointmentId) {
    Appointment appointment = getAppointment(appointmentId);
    appointment.cancel();
    repository.update(appointment);
    slotService.markSlotFree(appointment.getSlotId());
  }

  @Transactional
  public Appointment reschedule(RescheduleAppointmentRequest request) {
    Appointment appointment = getAppointment(request.appointmentId());

    Slot oldSlot = slotService.getSlot(appointment.getSlotId());

    Slot newSlot = slotService.getValidSlotForAppointment(request.slotId());

    appointment.reschedule(newSlot, request.comment());
    validatePatientHasNoIntersections(appointment.getPatientId(), appointment);

    slotService.markSlotFree(oldSlot);
    slotService.markSlotBooked(newSlot);
    repository.update(appointment);
    return appointment;
  }

  @Transactional
  public Appointment updateComment(UpdateAppointmentCommentRequest request) {
    Appointment appointment = getAppointment(request.appointmentId());
    appointment.updateComment(request.comment());
    repository.update(appointment);
    return appointment;
  }

  @Transactional
  public Appointment submit(SubmitAppointmentRequest request) {
    // Validate if users are in the system.
    practitionerService.findById(request.practitionerId());
    patientService.findById(request.patientId());

    Slot slot = slotService.getValidSlotForAppointment(request.slotId());

    Appointment appointment = createNewAppointment(request, slot);

    validatePatientHasNoIntersections(request.patientId(), appointment);

    slotService.markSlotBooked(slot);
    repository.create(appointment);
    return appointment;
  }

  private Appointment createNewAppointment(SubmitAppointmentRequest request, Slot slot) {
    return factory.create(
        slot.getId(),
        request.patientId(),
        slot.getService().getName(),
        request.practitionerId(),
        request.comment(),
        slot.getStartTime(),
        slot.getEndTime(),
        AppointmentStatus.SCHEDULED
    );
  }

  private void validatePatientHasNoIntersections(@Nonnull UUID patientId,
      @Nonnull Appointment appointment)
      throws AppointmentValidationException {
    var appointments = repository.findPatientOverlappingAppointments(
        patientId,
        appointment.getStartTime(),
        appointment.getEndTime(),
        appointment.getId()
    );

    if (!appointments.isEmpty()) {
      throw new AppointmentValidationException("Patient has intersection with other appointments");
    }
  }
}
