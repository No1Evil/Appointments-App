package dev.tsumakov.appointments.slot;

import dev.tsumakov.appointments.slot.exception.SlotIsTakenException;
import dev.tsumakov.appointments.slot.exception.SlotNotFoundException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.List;
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
    Slot slot = slotRepository.findByWithLock(id)
            .orElseThrow(() -> new SlotNotFoundException("Slot with id " + id + " not found"));

    slot.validateNotExpired();

    if (!slot.isFree()) {
      throw new SlotIsTakenException("Slot is not available");
    }

    return slot;
  }

  @Transactional
  public Long create(Slot slot) {
    return slotRepository.create(slot);
  }

  public List<Slot> getByFilter(@Nullable SlotParams params) {
    if (params == null) {
      return slotRepository.findAll();
    }
    return slotRepository.listByFilter(params);
  }

  @Transactional
  public void markSlotBooked(@Nonnull Long slotId) {
    Slot slot = getSlot(slotId);
    slot.markAsBooked();
    slotRepository.update(slot);
  }

  @Transactional
  public void markSlotFree(@Nonnull Long slotId) {
    Slot slot = getSlot(slotId);
    slot.markAsFree();
    slotRepository.update(slot);
  }

  @Transactional
  public void markSlotFree(@Nonnull Slot slot) {
    slot.markAsFree();
    slotRepository.update(slot);
  }

  @Transactional
  public void markSlotBooked(@Nonnull Slot slot) {
    slot.markAsBooked();
    slotRepository.update(slot);
  }
}
