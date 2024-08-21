package com.sevenwonders.domain.repository

import com.sevenwonders.domain.model.City
import com.sevenwonders.utils.Utils.toWonder
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class CityDB {

    object Cities : Table() {
        private val id = integer("id").autoIncrement()
        val name = varchar("name", length = 30)
        val resource = varchar("resource", length = 30)
        val face = char("face")
        val wonders = text("wonders")

        override val primaryKey = PrimaryKey(id)
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(city: City): String = dbQuery {
        Cities.insert {
            it[name] = city.name
            it[resource] = city.resource
            it[face] = city.face
            it[wonders] = city.wonders.joinToString("@")
        }[Cities.name]
    }

    suspend fun read(name: String): List<City> {
        return dbQuery {
            Cities.select { Cities.name eq name }
                .map {
                    City(
                        it[Cities.name],
                        it[Cities.resource],
                        it[Cities.face],
                        it[Cities.wonders].toWonder()
                    )
                }
        }
    }

    suspend fun readBy(face: Char): List<City> {
        return dbQuery {
            Cities.select { Cities.face eq face }
                .map {
                    City(
                        it[Cities.name],
                        it[Cities.resource],
                        it[Cities.face],
                        it[Cities.wonders].toWonder()
                    )
                }
        }
    }

    suspend fun readAll(): List<City> {
        return dbQuery {
            Cities.selectAll()
                .map {
                    City(
                        it[Cities.name],
                        it[Cities.resource],
                        it[Cities.face],
                        it[Cities.wonders].toWonder()
                    )
                }
        }
    }
}
