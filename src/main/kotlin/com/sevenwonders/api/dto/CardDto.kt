package com.sevenwonders.api.dto

import com.sevenwonders.domain.model.Card
import kotlinx.serialization.Serializable

@Serializable
data class CardDto(
    val age: AgeDTO,
    val color: ColorDTO,
    val name: String,
    val players: Int,
    val cost: String,
    val gives: String,
    val freeConstructions: String
) {
    enum class AgeDTO {
        I, II, III
    }

    enum class ColorDTO {
        BRUN, GRIS, JAUNE, VERT, BLEU, ROUGE, VIOLET
    }
}

fun Card.toDto(): CardDto {
    return CardDto(
        CardDto.AgeDTO.valueOf(age.name),
        CardDto.ColorDTO.valueOf(color.name),
        name,
        players,
        cost,
        gives,
        freeConstructions
    )
}
