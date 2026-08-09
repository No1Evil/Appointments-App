package dev.tsumakov.appointments.common.factory;

import io.github.robsonkades.uuidv7.UUIDv7;
import jakarta.inject.Singleton;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public final class UuidFactoryImpl implements UuidFactory {

  @Override
  public UUID generate() {
    return UUIDv7.randomUUID();
  }
}
