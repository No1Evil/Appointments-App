package dev.tsumakov.appointments.slot

import dev.tsumakov.appointments.AbstractPostgresITSpec
import dev.tsumakov.appointments.slot.exception.SlotNotFoundException
import dev.tsumakov.appointments.slot.web.response.SlotResponse
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject

@MicronautTest
class SlotControllerITSpec extends AbstractPostgresITSpec {

    @Inject
    @Client("/")
    HttpClient client;

    SlotService slotService = Mock()

    @MockBean(SlotService)
    SlotService slotService() {
        return slotService
    }

    void "getSlotById should return slot by id"() {
        given:
        def slotId = 1L
        def slot = Slot.builder()
                .id(slotId)
                .build()

        when:
        def request = HttpRequest.GET("/api/slots/${slotId}")
        def response = client.toBlocking().exchange(request, SlotResponse)

        then:
        1 * slotService.getSlot(slotId) >> slot

        response.status() == HttpStatus.OK
        response.body().id() == slotId
    }

    void "getSlotById should return 404 when slot not found"() {
        given:
        def slotId = 1L
        def slot = Slot.builder()
                .id(slotId)
                .build()

        when:
        def request = HttpRequest.GET("/api/slots/${slotId}")
        client.toBlocking().exchange(request, SlotResponse)

        then:
        1 * slotService.getSlot(slotId) >> { throw new SlotNotFoundException("Not found") }

        def ex = thrown(HttpClientResponseException)
        ex.status == HttpStatus.NOT_FOUND
    }

    void "get should return all slots"() {
        given:
        def slots = [
                Slot.builder().id(1L).build(),
                Slot.builder().id(2L).build()
        ]

        when:
        def request = HttpRequest.GET("/api/slots")
        def response = client.toBlocking().exchange(
                request, Argument.listOf(SlotResponse))

        then:
        1 * slotService.getByFilter(_) >> slots

        response.status() == HttpStatus.OK
        response.body().size() == 2
        response.body()*.id() == [slots[0].id, slots[1].id]
    }
}