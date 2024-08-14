package com.sevenwonders.api.dto

import com.sevenwonders.domain.model.City
import kotlinx.serialization.Serializable

@Serializable
data class CityDTO(
    val name: String,
    val resource: String,
    val face: Char,
    val wonders: List<WonderDTO>
) {
    @Serializable
    data class WonderDTO(
        val level: Int,
        val cost: String,
        val gives: String
    )
}

fun City.toDto() = CityDTO(name, resource, face, wonders.toDto())

fun List<City.Wonder>.toDto() = this.map { CityDTO.WonderDTO(it.level, it.cost, it.gives) }