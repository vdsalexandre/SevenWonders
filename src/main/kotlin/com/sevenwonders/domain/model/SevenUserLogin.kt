package com.sevenwonders.domain.model

import java.time.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SevenUserLogin(val username: String, val password: String)

fun SevenUserLogin.toDomain() = SevenUser(
    username, password, LocalDateTime.now()
)
