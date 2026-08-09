package dev.tsumakov.appointments.service.mapper;

import dev.tsumakov.appointments.service.ServiceCategory;
import dev.tsumakov.appointments.service.web.response.ServiceCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.JAKARTA)
public interface ServiceCategoryMapper {

  ServiceCategoryResponse toResponse(ServiceCategory domain);

}
