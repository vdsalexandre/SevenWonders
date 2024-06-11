package com.sevenwonders.api.resource

import com.sevenwonders.utils.Utils.getCardsFromDirectory
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.http.content.staticFiles
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.io.File

fun Application.configureCardsResource() {

    routing {
        get("/") {
            val cards = getCardsFromDirectory("/static/im")
            call.respond(HttpStatusCode.OK, cards)
        }

        staticFiles("/static", File("im"))
    }
}
