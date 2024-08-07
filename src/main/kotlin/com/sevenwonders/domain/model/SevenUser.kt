package com.sevenwonders.domain.model

import com.sevenwonders.utils.LocalDateTimeSerializer
import java.time.LocalDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

@Serializable
data class SevenUser(
    val username: String,
    val password: String,
    @Serializable(with = LocalDateTimeSerializer::class) val lastConnection: LocalDateTime
)

class SevenUserDB {

    object SevenUsers : Table() {
        val id = integer("id").autoIncrement()
        val username = varchar("username", length = 50)
        val password = varchar("password", length = 255)
        val lastConnection = datetime("lastConnection").defaultExpression(CurrentDateTime)

        override val primaryKey = PrimaryKey(id)
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(user: SevenUser): Int = dbQuery {
        SevenUsers.insert {
            it[username] = user.username
            it[password] = user.password
            it[lastConnection] = user.lastConnection
        }[SevenUsers.id]
    }

    suspend fun read(id: Int): SevenUser? {
        return dbQuery {
            SevenUsers.select { SevenUsers.id eq id }
                .map {
                    SevenUser(
                        it[SevenUsers.username],
                        it[SevenUsers.password],
                        it[SevenUsers.lastConnection],
                    )
                }
                .singleOrNull()
        }
    }

    suspend fun update(id: Int, user: SevenUser) {
        dbQuery {
            SevenUsers.update({ SevenUsers.id eq id }) {
                it[username] = user.username
                it[password] = user.password
                it[lastConnection] = LocalDateTime.now()
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            SevenUsers.deleteWhere { SevenUsers.id.eq(id) }
        }
    }
}