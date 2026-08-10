package dev.tsumakov.appointments

import io.micronaut.test.support.TestPropertyProvider
import org.testcontainers.containers.PostgreSQLContainer
import spock.lang.Specification

abstract class AbstractPostgresITSpec extends Specification implements TestPropertyProvider {

    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16.1")
            .withInitScript("init-schema.sql")

    static {
        POSTGRES.start()
    }

    @Override
    Map<String, String> getProperties() {
        [
                "datasources.default.url"     : POSTGRES.jdbcUrl,
                "datasources.default.username": POSTGRES.username,
                "datasources.default.password": POSTGRES.password,
                "datasources.liquibase.url"   : POSTGRES.jdbcUrl,
                "datasources.liquibase.username": POSTGRES.username,
                "datasources.liquibase.password": POSTGRES.password,
        ]
    }
}
