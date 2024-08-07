package com.sevenwonders.api.resource

import com.sevenwonders.domain.service.CityServiceAdapter
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureCitiesResource() {
    val cityService: CityServiceAdapter by inject()

    routing {
        get("/cities") {
            val cities = cityService.getAllCities()
            call.respond(HttpStatusCode.OK, cities)
        }
    }
}