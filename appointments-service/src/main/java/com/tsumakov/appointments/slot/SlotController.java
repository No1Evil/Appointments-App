package dev.tsumakov.appointments.slot;

import dev.tsumakov.appointments.slot.mapper.SlotMapper;
import dev.tsumakov.appointments.slot.web.response.SlotResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Controller("/slots")
@RequiredArgsConstructor
public class SlotController {

  private final SlotService slotService;
  private final SlotMapper slotMapper;

  @Get("/{id}")
  public HttpResponse<SlotResponse> get(Long id) {
    var result = slotService.getSlot(id);
    return HttpResponse.ok(slotMapper.toResponse(result));
  }

  @Get("{?params*}")
  public HttpResponse<List<SlotResponse>> getSlots(@Nullable SlotParams params) {
    var result = slotService.getSlots(params);
    var response = result.stream().map(slotMapper::toResponse).toList();
    return HttpResponse.ok(response);
  }

}
