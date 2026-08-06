package dev.tsumakov.appointments.appointment.mapper;

import dev.tsumakov.appointments.appointment.Appointment;
import dev.tsumakov.appointments.appointment.AppointmentsParams;
import dev.tsumakov.appointments.appointment.web.request.FilterAppointmentsRequest;
import dev.tsumakov.appointments.appointment.web.response.AppointmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.JAKARTA)
public interface AppointmentMapper {

  AppointmentResponse toResponse(Appointment appointment);

  AppointmentsParams toDomain(FilterAppointmentsRequest request);
}
