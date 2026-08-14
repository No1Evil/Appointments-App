package dev.tsumakov.appointments.practitioner;

import dev.tsumakov.appointments.practitioner.exception.PractitionerNotFoundException;
import jakarta.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PractitionerService {

  private final PractitionerRepository repository;

  public Practitioner findById(UUID uuid) {
    return repository.findById(uuid).orElseThrow(
        () -> new PractitionerNotFoundException("Practitioner with id " + uuid + " not found")
    );
  }

  public List<Practitioner> listAll() {
    return repository.findAll();
  }

  public List<Practitioner> listByFilter(@Nullable PractitionerParams params) {
    if (params == null)
      return listAll();
    return repository.listByFilter(params);
  }
}
