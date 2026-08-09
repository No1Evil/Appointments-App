package dev.tsumakov.appointments.slot.mapper;

import dev.tsumakov.appointments.slot.Slot;
import dev.tsumakov.appointments.slot.SlotParams;
import dev.tsumakov.appointments.slot.web.request.FilterSlotsRequest;
import dev.tsumakov.appointments.slot.web.response.SlotResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.JAKARTA)
public interface SlotMapper {

  SlotResponse toResponse(Slot slot);

  SlotParams toParams(FilterSlotsRequest request);
}
