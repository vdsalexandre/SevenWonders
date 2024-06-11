package com.sevenwonders.api.dto

data class CardDto(
    val age: AgeDto,
    val color: ColorDto,
    val name: String,
    val gives: CardGiveDto,
    val players: Int,
    val cost: Int
) {
    data class CardGiveDto(var quantity: Int, var give: String)

    enum class AgeDto { I, II, III }

    enum class ColorDto { BRUN, JAUNE, VERT, GRIS, BLEU, ROUGE, VIOLET }
}
