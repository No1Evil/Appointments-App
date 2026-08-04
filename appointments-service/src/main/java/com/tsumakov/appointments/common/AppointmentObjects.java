package dev.tsumakov.appointments.common;

import dev.tsumakov.appointments.common.exception.DateValidationException;
import dev.tsumakov.appointments.common.exception.StringValidationException;

import java.time.OffsetDateTime;
import java.util.Objects;

public class AppointmentObjects {

    private AppointmentObjects() {
        throw new AssertionError("Nope.");
    }

    public static void requireValidDates(OffsetDateTime start, OffsetDateTime end) {
        Objects.requireNonNull(start, "Start date can not be null");
        Objects.requireNonNull(end, "End date can not be null");

        if (start.isAfter(end)) {
            throw new DateValidationException("Start date can not be after end date");
        }
    }

    public static void requireNotBlank(String str, String fieldName) {
        if (str == null || str.isBlank()) {
            throw new StringValidationException(fieldName + " can not be null or blank");
        }
    }
}
