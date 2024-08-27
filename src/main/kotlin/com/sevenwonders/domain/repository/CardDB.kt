package com.sevenwonders.domain.repository

import com.sevenwonders.domain.model.Card
import com.sevenwonders.domain.model.SevenUserDB
import com.sevenwonders.utils.Utils.toElement
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class CardDB {

    object Cards : Table() {
        val id = integer("id").autoIncrement()
        val age = enumeration<Card.Age>("age")
        val color = enumeration<Card.Color>("color")
        val name = varchar("name", length = 255)
        val players = integer("players")
        val cost = varchar("cost", length = 30)
        val gives = varchar("gives", length = 255)
        val freeConstructions = varchar("freeConstructions", length = 255)

        override val primaryKey = PrimaryKey(id)
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun read(id: Int): Card? {
        return dbQuery {
            Cards
                .select { Cards.id eq id }
                .map { toCard(it) }
                .singleOrNull()
        }
    }

    suspend fun readAll(): List<Card> {
        return dbQuery {
            Cards
                .selectAll()
                .map { toCard(it) }
        }
    }

    suspend fun readBy(color: Card.Color): List<Card> {
        return dbQuery {
            Cards
                .select { Cards.color eq color }
                .map { toCard(it) }
        }
    }

    suspend fun readByPlayers(players: Int): List<Card> {
        return dbQuery {
            Cards
                .select { Cards.players lessEq players }
                .map { toCard(it) }
        }
    }

    suspend fun readByAge(age: Card.Age): List<Card> {
        return dbQuery {
            Cards
                .select { Cards.age eq age }
                .map { toCard(it) }
        }
    }

    suspend fun readByAgeAndPlayers(age: Card.Age, players: Int): List<Card> {
        return dbQuery {
            Cards
                .select { Cards.age eq age and (Cards.players lessEq players) }
                .map { toCard(it) }
        }
    }

    suspend fun update(id: Int, card: Card) {
        dbQuery {
            SevenUserDB.SevenUsers.update({ Cards.id eq id }) {
                it[Cards.age] = card.age
                it[Cards.color] = card.color
                it[Cards.name] = card.name
                it[Cards.players] = card.players
                it[Cards.cost] = card.cost
                it[Cards.gives] = card.gives
                it[Cards.freeConstructions] = card.freeConstructions
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            Cards.deleteWhere { Cards.id.eq(id) }
        }
    }

    private fun toCard(row: ResultRow) = Card(
        row[Cards.age],
        row[Cards.color],
        row[Cards.name],
        row[Cards.players],
        row[Cards.cost].toElement(),
        row[Cards.gives].toElement(),
        row[Cards.freeConstructions],
    )
}