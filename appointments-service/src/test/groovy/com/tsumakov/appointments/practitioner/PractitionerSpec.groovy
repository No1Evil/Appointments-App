package dev.tsumakov.appointments.practitioner

import spock.lang.Specification

class PractitionerSpec extends Specification {

    static def createPractitioner(
            UUID id = UUID.randomUUID(),
            String firstName = "first-name",
            String lastName = "last-name"
    ) {
        return Practitioner.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .service(null)
                .build()
    }

    def "equals and hashCode should only compare the id field"() {
        given: "two practitioners with the same id but different names"
        def id = UUID.randomUUID()
        def practitioner1 = createPractitioner(id, "first-name-1", "last-name-1")
        def practitioner2 = createPractitioner(id, "first-name-2", "last-name-2")

        and: "a practitioner with a different code"
        def practitioner3 = createPractitioner(UUID.randomUUID(), "first-name-1", "last-name-1")

        expect: "practitioners with the same code to be equal and have the same hashCode"
        practitioner1 == practitioner2
        practitioner1.hashCode() == practitioner2.hashCode()

        and: "practitioners with different codes to not be equal"
        practitioner1 != practitioner3
    }

}
