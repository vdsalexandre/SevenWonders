package com.sevenwonders.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val age: Age,
    val color: Color,
    val name: String,
    val gives: CardGive,
    val players: Int,
    val cost: Int
) {

    @Serializable
    data class CardGive(
        var quantity: Int,
        var give: String
    )

    enum class Age {
        I, II, III
    }

    enum class Color {
        BRUN, GRIS, JAUNE, VERT, BLEU, ROUGE, VIOLET
    }
}
