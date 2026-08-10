package dev.tsumakov.appointments.patient

import dev.tsumakov.appointments.AbstractPostgresITSpec
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.springframework.jdbc.core.JdbcTemplate

@MicronautTest
class PatientRepositoryITSpec extends AbstractPostgresITSpec {

    @Inject
    PatientRepository repository

    @Inject
    JdbcTemplate jdbcTemplate

    static UUID patientId = UUID.randomUUID()
    static String firstName = "First-name"
    static String lastName = "Last-name"

    static def createPatient() {
        return Patient.builder()
                .id(patientId)
                .firstName(firstName)
                .lastName(lastName)
                .build()
    }

    def setup() {
        jdbcTemplate.execute("delete from patients")
    }

    void "should create and find a patient"() {
        given:
        def patient = createPatient()

        when:
        repository.create(patient)
        def found = repository.findBy(patientId)

        then:
        found.present
        def result = found.get()
        result.id == patient.id
        result.firstName == patient.firstName
        result.lastName == patient.lastName
    }

    void "should return empty when patient not found"() {
        expect:
        repository.findBy(patientId).isEmpty()
    }

    void "should return empty list on empty table"() {
        expect:
        repository.findAll().isEmpty()
    }

    void "should list after creating"() {
        given:
        def patient = createPatient()

        when:
        repository.create(patient)

        then:
        repository.findAll().size() == 1
    }

    void "should delete patient successfully"() {
        given:
        def patient = createPatient()

        when:
        repository.create(patient)
        repository.delete(patient.id)

        then:
        repository.findBy(patient.id).isEmpty()
    }

    void "should not delete unexisting patient"() {
        when:
        def isDeleted = repository.delete(patientId)

        then:
        !isDeleted
    }

    void "should delete patient"() {
        given:
        def patient = createPatient()

        when:
        repository.create(patient)
        repository.delete(patient.id)

        then:
        repository.findBy(patient.id).isEmpty()
        repository.findAll().isEmpty()
    }

    void "should not update unexisting patient"() {
        given:
        def patient = createPatient()

        when:
        def isUpdated = repository.update(patient)

        then:
        !isUpdated
    }
}
