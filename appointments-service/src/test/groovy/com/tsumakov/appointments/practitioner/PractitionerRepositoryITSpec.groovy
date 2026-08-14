package dev.tsumakov.appointments.practitioner

import dev.tsumakov.appointments.AbstractPostgresITSpec
import dev.tsumakov.appointments.service.ServiceCategory
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.springframework.jdbc.core.JdbcTemplate

@MicronautTest
class PractitionerRepositoryITSpec extends AbstractPostgresITSpec {

    @Inject
    PractitionerRepository repository

    @Inject
    JdbcTemplate jdbcTemplate

    static UUID practitionerId = UUID.randomUUID()
    static String firstName = "First-name"
    static String lastName = "Last-name"

    ServiceCategory serviceCategory = GroovyMock(
            ServiceCategory,
            constructorArgs: ["gp", "General Practice"]
    )

    def createPractitioner() {
        return Practitioner.builder()
                .id(practitionerId)
                .firstName(firstName)
                .lastName(lastName)
                .service(serviceCategory)
                .build()
    }

    def setup() {
        jdbcTemplate.execute("delete from practitioners")
    }

    void "should create and find a practitioner"() {
        given:
        def practitioner = createPractitioner()

        when:
        repository.create(practitioner)
        def found = repository.findById(practitioner.id)

        then:
        found.isPresent()

        and:
        def result = found.get()
        result.id == practitioner.id
        result.firstName == practitioner.firstName
        result.lastName == practitioner.lastName
        result.service == practitioner.service
    }

    void "should return empty when practitioner not found"() {
        expect:
        repository.findById(practitionerId).isEmpty()
    }

    void "should return empty list on empty table"() {
        expect:
        repository.findAll().isEmpty()
    }

    void "should list after creating"() {
        given:
        def practitioner = createPractitioner()

        when:
        repository.create(practitioner)

        then:
        repository.findAll().size() == 1
    }

    void "should delete practitioner successfully"() {
        given:
        def practitioner = createPractitioner()

        when:
        repository.create(practitioner)
        repository.delete(practitioner.id)

        then:
        repository.findById(practitioner.id).isEmpty()
    }

    void "should not delete unexisting practitioner"() {
        when:
        def isDeleted = repository.delete(practitionerId)

        then:
        !isDeleted
    }

    void "should delete practitioner"() {
        given:
        def practitioner = createPractitioner()

        when:
        repository.create(practitioner)
        repository.delete(practitioner.id)

        then:
        repository.findById(practitioner.id).isEmpty()
        repository.findAll().isEmpty()
    }

    void "should not update unexisting practitioner"() {
        given:
        def practitioner = createPractitioner()

        when:
        def isUpdated = repository.update(practitioner)

        then:
        !isUpdated
    }
}
