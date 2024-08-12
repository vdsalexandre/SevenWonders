package com.sevenwonders.api.resource

import com.sevenwonders.api.dto.toDto
import com.sevenwonders.domain.model.City
import com.sevenwonders.domain.service.CityServiceAdapter
import com.sevenwonders.utils.Utils
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

        //    val text = "city_alexandria_a_2P-3W@2G-1PO1GO1BO1P@2V-7W.jpg"

            if (!c.isNullOrEmpty()){
                val city: City = Utils.convertCity(c)
                call.respond(HttpStatusCode.OK, city)
            }
        }
    }
}