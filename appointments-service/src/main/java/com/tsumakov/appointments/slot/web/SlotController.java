package dev.tsumakov.appointments.slot.web;

import dev.tsumakov.appointments.slot.SlotParams;
import dev.tsumakov.appointments.slot.SlotService;
import dev.tsumakov.appointments.slot.mapper.SlotMapper;
import dev.tsumakov.appointments.slot.web.request.FilterSlotsRequest;
import dev.tsumakov.appointments.slot.web.response.SlotResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.RequestBean;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Tag(name = "slot")
@Controller("/api/slots")
@RequiredArgsConstructor
public class SlotController {

  private final SlotService slotService;
  private final SlotMapper slotMapper;

  @Get("/{id}")
  @Operation(operationId = "getSlotById", summary = "get slot by id")
  public HttpResponse<SlotResponse> getById(Long id) {
    var result = slotService.getSlot(id);
    return HttpResponse.ok(slotMapper.toResponse(result));
  }

  @Get("{?request*}")
  @Operation(operationId = "getSlots", summary = "get all slots")
  public HttpResponse<List<SlotResponse>> get(
      @Valid @Nullable @RequestBean FilterSlotsRequest request
  ) {
    var result = slotService.getByFilter(slotMapper.toParams(request));
    var response = result.stream().map(slotMapper::toResponse).toList();
    return HttpResponse.ok(response);
  }

}
