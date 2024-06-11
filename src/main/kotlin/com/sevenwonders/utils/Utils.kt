package com.sevenwonders.utils

import com.sevenwonders.domain.model.Card
import com.sevenwonders.domain.model.Card.CardGive
import com.sevenwonders.domain.model.Card.Age
import com.sevenwonders.domain.model.Card.Color
import java.io.File

object Utils {

    fun getCardsFromDirectory(directory: String): List<Card> {
        val cards: MutableList<Card> = mutableListOf()
        val directoryPath =
            object {}.javaClass.getResource(directory)?.file ?: throw NoSuchFileException(File(directory))
        val dir = File(directoryPath)
        val files = dir.listFiles()?.filter { it.isFile }

        if (!files.isNullOrEmpty()) {
            for (file in files) {
                val data = file.name.removeSuffix(".jpg").split("_")
                cards.add(
                    Card(
                        age = Age.valueOf(data[1]),
                        color = Color.valueOf(data[2].uppercase()),
                        name = data[3],
                        gives = convertGive(data[4]),
                        players = Integer.valueOf(data[5]),
                        cost = Integer.valueOf(data[6])
                    )
                )
            }
        }
        return cards
    }

    private fun convertGive(giveCode: String): CardGive {
        val parts = giveCode.split("-")
        val quantity = Integer.valueOf(parts[0])
        var giveTemp = convertGivePart(parts[1][0])

        if (parts.size > 2) {
            giveTemp += convertGiveOperator(parts[2])
            giveTemp += convertGivePart(parts[1][1])
        }

        return CardGive(
            quantity = quantity,
            give = giveTemp
        )
    }

    private fun convertGiveOperator(giveOperator: String) = giveOperator.replace("O", " ou ")

    private fun convertGivePart(givePart: Char) = when (givePart) {
        'A' -> "argile"
        'P' -> "pierre"
        'G' -> "or"
        'B' -> "bois"
        'T' -> "tissu"
        'V' -> "verre"
        'R' -> "papier"
        'M' -> "pieces"
        else -> ""
    }
}