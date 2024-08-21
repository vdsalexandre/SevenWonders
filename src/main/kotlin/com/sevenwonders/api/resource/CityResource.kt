package com.sevenwonders.api.resource

import com.sevenwonders.api.dto.toDto
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
            val cities = cityService.getAllCities().map { it.toDto() }
            call.respond(HttpStatusCode.OK, cities)
        }

        get("/cities/{city}") {
            val c = call.parameters["city"]

            if (!c.isNullOrEmpty()){
                val cities = cityService.getCity(c)
                call.respond(HttpStatusCode.OK, cities.map { it.toDto() })
            }
        }

        get("/cities/face/{face}") {
            val face = call.parameters["face"]?.toCharArray()?.get(0)

            if (face != null ){
                val cities = cityService.getCityBy(face)
                call.respond(HttpStatusCode.OK, cities.map { it.toDto() })
            }
        }
    }
}