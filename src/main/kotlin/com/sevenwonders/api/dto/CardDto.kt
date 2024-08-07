package com.sevenwonders.api.dto

import com.sevenwonders.domain.model.Card
import kotlinx.serialization.Serializable

@Serializable
data class CardDto(
    val age: Card.Age,
    val color: Card.Color,
    val name: String,
    val gives: String,
    val givesQuantity: Int,
    val players: Int,
    val cost: Int
)

fun Card.toDto() = CardDto(age, color, name, gives, giveQuantity, players, cost)