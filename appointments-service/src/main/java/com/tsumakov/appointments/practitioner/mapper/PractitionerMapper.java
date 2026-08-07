package dev.tsumakov.appointments.practitioner.mapper;

import dev.tsumakov.appointments.practitioner.Practitioner;
import dev.tsumakov.appointments.practitioner.web.response.PractitionerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.JAKARTA)
public interface PractitionerMapper {

  PractitionerResponse toResponse(Practitioner practitioner);

}
