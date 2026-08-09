package dev.tsumakov.appointments.practitioner;

import jakarta.annotation.Nullable;

public record PractitionerParams(
    @Nullable String serviceCode
) {

}
