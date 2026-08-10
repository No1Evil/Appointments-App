package dev.tsumakov.appointments.patient

import dev.tsumakov.appointments.common.exception.StringValidationException
import dev.tsumakov.appointments.common.factory.UuidFactory
import spock.lang.Specification

class PatientFactorySpec extends Specification {

    UuidFactory uuidFactory = Mock()
    PatientFactory factory = new PatientFactory(uuidFactory)

    void "should create patient with all fields correctly"() {
        given: "input data"
        def expectedUuid = UUID.randomUUID()
        def firstName = "First"
        def lastName = "Last"

        and: "mocked UUID generation"
        1 * uuidFactory.generate() >> expectedUuid

        when: "factory creates patient"
        def patient = factory.create(firstName, lastName)

        then: "all fields are mapped correctly"
        patient.id == expectedUuid
        patient.firstName == firstName
        patient.lastName == lastName
    }

    void "should throw exception when firstName is null or blank"() {
        given: "input data with null first name"
        def firstName = null
        def lastName = "Last"

        when: "factory creates patient"
        factory.create(firstName, lastName)

        then: "should throw exception"
        thrown(StringValidationException)

        and: "blank first name"
        def firstName2 = " "

        when: "factory creates patient"
        factory.create(firstName2, lastName)

        then: "should throw exception"
        thrown(StringValidationException)
    }

    void "should throw exception when lastName is null or blank"() {
        given: "input data with null first name"
        def firstName = "First"
        def lastName = null

        when: "factory creates patient"
        factory.create(firstName, lastName)

        then: "should throw exception"
        thrown(StringValidationException)

        and: "blank first name"
        def lastName2 = " "

        when: "factory creates patient"
        factory.create(firstName, lastName2)

        then: "should throw exception"
        thrown(StringValidationException)
    }

}
