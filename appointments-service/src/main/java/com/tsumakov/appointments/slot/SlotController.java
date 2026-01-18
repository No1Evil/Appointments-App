package dev.tsumakov.appointments.slot;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Controller("slots")
@RequiredArgsConstructor
public class SlotController {
  private final SlotService slotService;

  @Get("/{id}")
  public Slot getSlot(Long id) {
    return slotService.getSlot(id);
  }

  @Get("{?params*}")
  public List<Slot> getSlots(@Nullable SlotParams params) {
    return slotService.getSlots(params);
  }

}
