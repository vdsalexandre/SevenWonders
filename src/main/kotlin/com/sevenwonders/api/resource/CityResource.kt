package com.sevenwonders.api.resource

import com.sevenwonders.api.dto.CityDTO
import com.sevenwonders.api.dto.toDto
import com.sevenwonders.domain.service.CityServiceAdapter
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlin.random.Random
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
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get("/cities/face/{face}") {
            val face = call.parameters["face"]?.toCharArray()?.get(0)

            if (face != null && "ab".contains(face, ignoreCase = true)){
                val cities = cityService.getCityBy(face).map { it.toDto() }
                call.respond(HttpStatusCode.OK, cities)
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get("/cities/random") {
            val cities = cityService.getAllCities().map { it.toDto() }
            val random = Random.nextInt(0, cities.size)

            call.respond(HttpStatusCode.OK, cities[random])
        }

        get("/cities/random/{face}") {
            val face = call.parameters["face"]?.toCharArray()?.get(0)

            if (face != null  && "ab".contains(face, ignoreCase = true)){
                val cities = cityService.getCityBy(face).map { it.toDto() }
                val random = Random.nextInt(0, cities.size)
                call.respond(HttpStatusCode.OK, cities[random])
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get("/cities/{players}/random/{face}") {
            val face = call.parameters["face"]?.toCharArray()?.get(0)
            val players = call.parameters["players"]

            try {
                if (face != null && "ab".contains(face, ignoreCase = true) && players != null){
                    val cities = cityService.getCityBy(face).map { it.toDto() }
                    val p = Integer.valueOf(players)
                    val randoms = mutableSetOf<Int>()
                    while (randoms.size < p)
                        randoms.add((cities.indices).random())
                    val randomCities = mutableListOf<CityDTO>()
                    for (r in randoms)
                        randomCities.add(cities[r])
                    call.respond(HttpStatusCode.OK, randomCities)
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get("/cities/{players}/random") {
            try {
                val players = Integer.valueOf(call.parameters["players"])
                val cities = cityService.getAllCities().map { it.toDto() }
                val randoms = List(players) { Random.nextInt(0, cities.size) }
                val randomCities: MutableList<CityDTO> = mutableListOf()
                for (r in randoms)
                    randomCities.add(cities[r])
                call.respond(HttpStatusCode.OK, randomCities)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}