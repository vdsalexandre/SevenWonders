package com.sevenwonders.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val age: Age,
    val color: Color,
    val name: String,
    val gives: String,
    val giveQuantity: Int,
    val players: Int,
    val cost: Int
) {

    enum class Age {
        I, II, III
    }

    enum class Color {
        BRUN, GRIS, JAUNE, VERT, BLEU, ROUGE, VIOLET
    }
}
