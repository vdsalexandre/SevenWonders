package com.sevenwonders.api.resource

import com.sevenwonders.api.dto.toDto
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
    }
}
