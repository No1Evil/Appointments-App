package dev.tsumakov.appointments.practitioner;

import dev.tsumakov.appointments.practitioner.exception.PractitionerNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PractitionerService {

  private final PractitionerRepository repository;

  public Practitioner findById(UUID uuid) {
    return repository.findBy(uuid).orElseThrow(
        () -> new PractitionerNotFoundException("Practitioner with id " + uuid + " not found")
    );
  }

  public List<Practitioner> listAll() {
    return repository.findAll();
  }
}
