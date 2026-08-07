package dev.tsumakov.appointments;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title = "Appointments application",
        version = "v0.1",
        description = "Appointments application API",
        contact = @Contact(url = "https://gitlab.com/no1evil/appointments-app", name = "Fjodor")
    )
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
