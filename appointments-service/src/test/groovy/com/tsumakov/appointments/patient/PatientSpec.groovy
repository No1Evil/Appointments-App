package dev.tsumakov.appointments.patient

import spock.lang.Specification

class PatientSpec extends Specification {

    static def createPatient(
            UUID id = UUID.randomUUID(),
            String firstName = "first-name",
            String lastName = "last-name"
    ) {
        return Patient.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .build()
    }

    def "equals and hashCode should only compare the id field"() {
        given: "two patients with the same id but different names"
        def id = UUID.randomUUID()
        def patient1 = createPatient(id, "first-name-1", "last-name-1")
        def patient2 = createPatient(id, "first-name-2", "last-name-2")

        and: "a patient with a different code"
        def patient3 = createPatient(UUID.randomUUID(), "first-name-1", "last-name-1")

        expect: "patients with the same code to be equal and have the same hashCode"
        patient1 == patient2
        patient1.hashCode() == patient2.hashCode()

        and: "patients with different codes to not be equal"
        patient1 != patient3
    }

}
