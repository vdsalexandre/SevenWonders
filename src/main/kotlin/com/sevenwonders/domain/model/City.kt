package com.sevenwonders.domain.model

data class City(
    val name: String,
    val resource: String,
    val face: Char,
    val wonders: List<Wonder>
) {
    data class Wonder(
        val level: Int,
        val cost: String,
        val gives: String
    )
}

data class CityToSave(
    val name: String,
    val resource: String,
    val face: Char,
    val wonders: String
)
