package com.sevenwonders.bootstrap

import com.sevenwonders.domain.model.SevenUserDB
import com.sevenwonders.domain.repository.CardDB
import com.sevenwonders.domain.repository.CityDB
import com.sevenwonders.utils.Utils.getCardsFromDirectory
import com.sevenwonders.utils.Utils.getCitiesFromDirectory
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val database = Database.connect(
            url = "jdbc:h2:mem:sevenwonders;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver",
            user = "root",
            password = "root"
        )

    transaction(database) {
        SchemaUtils.create(SevenUserDB.SevenUsers)
        SchemaUtils.create(CardDB.Cards)
        SchemaUtils.create(CityDB.Cities)

        val cards = getCardsFromDirectory("/im/cards")
        for (card in cards) {
            CardDB.Cards.insert {
                it[age] = card.age
                it[color] = card.color
                it[name] = card.name
                it[gives] = card.gives
                it[giveQuantity] = card.giveQuantity
                it[players] = card.players
                it[cost] = card.cost
            }
        }
        val cities = getCitiesFromDirectory("/im/cities")
        cities.forEach { city ->
            CityDB.Cities.insert {
                it[name] = city.name
                it[resource] = city.resource
                it[face] = city.face
                it[wonders] = city.wonders
//                it[wonders] = city.wonders.joinToString(separator = "@")
            }
        }
    }

//    routing {
//        post("/users") {
//            val user = call.receive<SevenUserLogin>()
//            val id = sevenUserDBService.create(user.toDomain())
//            call.respond(HttpStatusCode.Created, id)
//        }
//
//        get("/users/{id}") {
//            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
//            val user = sevenUserDBService.read(id)
//            if (user != null) {
//                call.respond(HttpStatusCode.OK, user)
//            } else {
//                call.respond(HttpStatusCode.NotFound)
//            }
//        }
//
//        put("/users/{id}") {
//            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
//            val user = call.receive<SevenUser>()
//            sevenUserDBService.update(id, user)
//            call.respond(HttpStatusCode.OK)
//        }
//
//        delete("/users/{id}") {
//            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
//            sevenUserDBService.delete(id)
//            call.respond(HttpStatusCode.OK)
//        }
//    }
}
