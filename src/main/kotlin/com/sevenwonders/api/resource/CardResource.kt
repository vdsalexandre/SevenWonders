package com.sevenwonders.api.resource

import com.sevenwonders.api.dto.toDto
import com.sevenwonders.domain.model.Card
import com.sevenwonders.domain.service.CardServiceAdapter
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureCardsResource() {

    val cardService: CardServiceAdapter by inject()

    routing {
        get("/") {
            val cards = cardService.getAllCards().map { it.toDto() }
            call.respond(HttpStatusCode.OK, cards)
        }

        get("/color/{color}") {
            val c = call.parameters["color"]

            if (!c.isNullOrEmpty()){
                try {
                    val cards = cardService.getCardsBy(Card.Color.valueOf(c.uppercase())).map { it.toDto() }
                    call.respond(HttpStatusCode.OK, cards)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get("/players/{players}") {
            val c = call.parameters["players"]

            if (!c.isNullOrEmpty()) {
                try {
                    val cards = cardService.getCardsByPlayers(Integer.valueOf(c)).map { it.toDto() }
                    call.respond(HttpStatusCode.OK, cards)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get("/age/{age}") {
            val c = call.parameters["age"]

            if (!c.isNullOrEmpty()) {
                try {
                    val cards = cardService.getCardsByAge(Card.Age.valueOf(c.uppercase())).map { it.toDto() }
                    call.respond(HttpStatusCode.OK, cards)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get("/age/{age}/players/{players}") {
            val c = call.parameters["age"]
            val p = call.parameters["players"]

            if (!c.isNullOrEmpty() && !p.isNullOrEmpty()) {
                try {
                    val cards = cardService.getCardsByAgeAndPlayers(Card.Age.valueOf(c.uppercase()), Integer.valueOf(p)).map { it.toDto() }
                    call.respond(HttpStatusCode.OK, cards)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
