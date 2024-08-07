package com.sevenwonders.domain.repository

import com.sevenwonders.domain.model.Card
import com.sevenwonders.domain.model.SevenUserDB
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
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
        val gives = varchar("gives", length = 255)
        val giveQuantity = integer("giveQuantity")
        val players = integer("players")
        val cost = integer("cost")

        override val primaryKey = PrimaryKey(id)
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(card: Card): String = dbQuery {
        Cards.insert {
            it[age] = card.age
            it[color] = card.color
            it[name] = card.name
            it[gives] = card.gives
            it[giveQuantity] = card.giveQuantity
            it[players] = card.players
            it[cost] = card.cost
        }[Cards.name]
    }

    suspend fun read(id: Int): Card? {
        return dbQuery {
            Cards.select { Cards.id eq id }
                .map {
                    Card(
                        it[Cards.age],
                        it[Cards.color],
                        it[Cards.name],
                        it[Cards.gives],
                        it[Cards.giveQuantity],
                        it[Cards.players],
                        it[Cards.cost],
                    )
                }
                .singleOrNull()
        }
    }

    suspend fun readAll(): List<Card> {
        return dbQuery {
            Cards.selectAll()
                .map {
                    Card(
                        it[Cards.age],
                        it[Cards.color],
                        it[Cards.name],
                        it[Cards.gives],
                        it[Cards.giveQuantity],
                        it[Cards.players],
                        it[Cards.cost],
                    )
                }
        }
    }

    suspend fun update(id: Int, card: Card) {
        dbQuery {
            SevenUserDB.SevenUsers.update({ Cards.id eq id }) {
                it[Cards.age] = card.age
                it[Cards.color] = card.color
                it[Cards.name] = card.name
                it[Cards.gives] = card.gives
                it[Cards.giveQuantity] = card.giveQuantity
                it[Cards.players] = card.players
                it[Cards.cost] = card.cost
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            Cards.deleteWhere { Cards.id.eq(id) }
        }
    }
}