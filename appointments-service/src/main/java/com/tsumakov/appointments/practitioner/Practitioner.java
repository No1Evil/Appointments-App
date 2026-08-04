package dev.tsumakov.appointments.practitioner;

import lombok.Data;

import java.util.UUID;

@Data
public class Practitioner {
    private UUID id;
    private String firstName;
    private String lastName;
    private String specialty;
}
