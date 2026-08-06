package dev.tsumakov.appointments.patient;

import dev.tsumakov.appointments.patient.exception.PatientNotFoundException;
import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

  private final PatientRepository repository;

  public Patient findById(@Nonnull UUID uuid) {
    return repository.findBy(uuid).orElseThrow(
        () -> new PatientNotFoundException("Patient with id " + uuid + " not found"));
  }

  public List<Patient> listAll() {
    return repository.findAll();
  }
}
