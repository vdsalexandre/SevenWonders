package com.sevenwonders.api.dto

import com.sevenwonders.domain.model.City
import kotlinx.serialization.Serializable

@Serializable
data class CityDTO(
    val name: String,
    val face: Char
)

fun City.toDto() = CityDTO(name, face)