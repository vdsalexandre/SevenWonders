package com.sevenwonders.domain.model

data class Card(
    val age: Age,
    val color: Color,
    val name: String,
    val players: Int,
    val cost: String,
    val gives: String,
    val freeConstructions: String
) {

    enum class Age {
        I, II, III
    }

    enum class Color {
        BRUN, GRIS, JAUNE, VERT, BLEU, ROUGE, VIOLET
    }
}
