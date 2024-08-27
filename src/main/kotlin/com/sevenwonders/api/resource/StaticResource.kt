package com.sevenwonders.api.resource

import io.ktor.server.application.Application
import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.routing

fun Application.configureStaticResource() {

    routing {
        staticResources("/static", "im")
    }
}