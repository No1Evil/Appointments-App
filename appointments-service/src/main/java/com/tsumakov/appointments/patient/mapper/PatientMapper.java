package dev.tsumakov.appointments.patient.mapper;

import dev.tsumakov.appointments.patient.Patient;
import dev.tsumakov.appointments.patient.web.response.PatientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.JAKARTA)
public interface PatientMapper {

  PatientResponse toResponse(Patient patient);
}
