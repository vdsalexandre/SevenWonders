package com.sevenwonders.domain.repository

import com.sevenwonders.domain.model.City
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class CityDB {

    object Cities : Table() {
        val id = integer("id").autoIncrement()
        val name = varchar("name", length = 30)
        val face = char("face")

        override val primaryKey = PrimaryKey(id)
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(city: City): String = dbQuery {
        Cities.insert {
            it[name] = city.name
            it[face] = city.face
        }[Cities.name]
    }

    suspend fun read(id: Int): City? {
        return dbQuery {
            Cities.select { Cities.id eq id }
                .map {
                    City(
                        it[Cities.name],
                        it[Cities.face]
                    )
                }
                .singleOrNull()
        }
    }

    suspend fun readAll(): List<City> {
        return dbQuery {
            Cities.selectAll()
                .map {
                    City(
                        it[Cities.name],
                        it[Cities.face]
                    )
                }
        }
    }

    suspend fun update(id: Int, city: City) {
        dbQuery {
            Cities.update({ Cities.id eq id }) {
                it[name] = city.name
                it[face] = city.face
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            CardDB.Cards.deleteWhere { Cities.id.eq(id) }
        }
    }
}