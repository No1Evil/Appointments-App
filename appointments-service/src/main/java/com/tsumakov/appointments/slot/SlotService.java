package dev.tsumakov.appointments.slot;

import dev.tsumakov.appointments.slot.exception.SlotIsTakenException;
import dev.tsumakov.appointments.slot.exception.SlotNotFoundException;
import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SlotService {

  private final SlotRepository slotRepository;

  public Slot getSlot(Long id) {
    return slotRepository.findBy(id)
        .orElseThrow(() -> new SlotNotFoundException("Slot with id " + id + " not found"));
  }

  public Slot getValidSlotForAppointment(@Nonnull Long id) {
    Slot slot = getSlot(id);

    slot.validateNotExpired();

    if (!slot.isFree()) {
      throw new SlotIsTakenException("Slot is not available");
    }

    return slot;
  }

  public List<Slot> getSlots(SlotParams params) {
    return slotRepository.listByFilter(params);
  }

  @Transactional
  public void markSlotScheduled(@Nonnull Long slotId) {
    Slot slot = getSlot(slotId);
    slot.markAsScheduled();
    slotRepository.update(slot);
  }

  @Transactional
  public void markSlotBooked(@Nonnull Long slotId) {
    Slot slot = getSlot(slotId);
    slot.markAsBooked();
    slotRepository.update(slot);
  }

  @Transactional
  public void markSlotBooked(@Nonnull Slot slot) {
    slot.markAsBooked();
    slotRepository.update(slot);
  }
}
