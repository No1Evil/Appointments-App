package dev.tsumakov.appointments.slot;

import jakarta.inject.Singleton;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class SlotService {
  private final SlotRepository slotRepository;

  public Slot getSlot(Long id) {
    return slotRepository.load(id);
  }

  public List<Slot> getSlots(SlotParams params) {
    return slotRepository.getSlots(params);
  }

  //TODO: add some methods to operate with slots
}
