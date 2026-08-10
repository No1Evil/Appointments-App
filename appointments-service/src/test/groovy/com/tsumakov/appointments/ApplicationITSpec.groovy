package dev.tsumakov.appointments

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.springframework.jdbc.core.JdbcTemplate
import jakarta.inject.Inject

@MicronautTest
class ApplicationITSpec extends AbstractPostgresITSpec {

    @Inject
    EmbeddedApplication<?> application

    @Inject
    JdbcTemplate jdbcTemplate

    void 'test it works'() {
        expect:
        application.running
    }

    void "should connect to postgres testcontainer and execute query"() {
        when:
        def result = jdbcTemplate.queryForObject("SELECT 1", Integer.class)

        then:
        result == 1
    }

}
