package com.sevenwonders.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class City(
    val name: String,
    val face: Char,
    val wonders: List<Wonder>
) {
    @Serializable
    data class Wonder(
        val level: Int,
        val cost: String,
        val gives: String
    )
}

fun String.toWonder(): List<City.Wonder> {
    val wonders = this.split("@")
    return wonders.mapIndexed { index, w ->
        val data = w.split("-")
        City.Wonder(
            level = index + 1,
            cost = data[0],
            gives = data[1]
        )
    }
}