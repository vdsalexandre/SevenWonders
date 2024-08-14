package com.sevenwonders.utils

import com.sevenwonders.domain.model.Card
import com.sevenwonders.domain.model.Card.Age
import com.sevenwonders.domain.model.Card.Color
import com.sevenwonders.domain.model.City
import com.sevenwonders.domain.model.CityToSave
import java.io.File

object Utils {

    fun getFileOrDirPath(directory: String) = object {}.javaClass.getResource(directory)?.file ?: throw NoSuchFileException(File(directory))

    fun getCitiesFromDirectory(directory: String): List<CityToSave> {
        val cities: MutableList<City> = mutableListOf()
        val citys: MutableList<CityToSave> = mutableListOf()
        val directoryPath = getFileOrDirPath(directory)
        val dir = File(directoryPath)
        val files = dir.listFiles()?.filter { it.isFile }

        if (!files.isNullOrEmpty()) {
            for (file in files) {
                citys.add(convertCityToSave(file.name))
            }
        }
        return citys
    }

    private fun convertCityToSave(cityName: String): CityToSave {
        val data = cityName.removePrefix("city_").removeSuffix(".jpg").split("_")

        return CityToSave(
            name = data[0],
            resource = convertGivePart(data[1][0]),
            face = data[2].toCharArray().first(),
            wonders = data[3]
        )
    }

    fun String.toWonder(): List<City.Wonder> {
        val wonders = this.split("@")
        return wonders.mapIndexed { index, w ->
            val data = w.split("-")
            City.Wonder(
                level = index + 1,
                cost = convertWonderCost(data[0]),
                gives = convertWonderCost(data[1])
            )
        }
    }

    private fun convertWonderCost(cost: String): String {
        var wonderCost = ""

        cost.toCharArray().map { c ->
            when (c) {
                'P' -> wonderCost += "pierre"
                'A' -> wonderCost += "argile"
                'B' -> wonderCost += "bois"
                'G' -> wonderCost += "minerai"
                'V' -> wonderCost += "verre"
                'T' -> wonderCost += "tissu"
                'R' -> wonderCost += "papyrus"
                'O' -> wonderCost += "ou"
                'W' -> wonderCost += "wonders"
                'X' -> wonderCost += "science tablette"
                'Y' -> wonderCost += "science compas"
                'Z' -> wonderCost += "science roue"
                'E' -> wonderCost += "et"
                'M' -> wonderCost += "pièces"
                'C' -> wonderCost += "carte à la fin de l'âge"
                'D' -> wonderCost += "carte gratuite dans la défausse"
                'K' -> wonderCost += "pour"
                'Q' -> wonderCost += "guilde copiée à la fin de la partie"
                'F' -> wonderCost += "bouclier"
                else -> wonderCost += c
            }
            wonderCost += " "
        }

        return wonderCost.trim()
    }

    fun getCardsFromDirectory(directory: String): List<Card> {
        val cards: MutableList<Card> = mutableListOf()
        val directoryPath =
            object {}.javaClass.getResource(directory)?.file ?: throw NoSuchFileException(File(directory))
        val dir = File(directoryPath)
        val files = dir.listFiles()?.filter { it.isFile }

        if (!files.isNullOrEmpty()) {
            for (file in files) {
                val data = file.name.removeSuffix(".jpg").split("_")
                val convertGive = convertGive(data[4])
                cards.add(
                    Card(
                        age = Age.valueOf(data[1]),
                        color = Color.valueOf(data[2].uppercase()),
                        name = data[3],
                        gives = convertGive.second,
                        giveQuantity = convertGive.first,
                        players = Integer.valueOf(data[5]),
                        cost = Integer.valueOf(data[6])
                    )
                )
            }
        }
        return cards
    }

    private fun convertGive(giveCode: String): Pair<Int, String> {
        val parts = giveCode.split("-")
        val quantity = Integer.valueOf(parts[0])
        var giveTemp = convertGivePart(parts[1][0])

        if (parts.size > 2) {
            giveTemp += convertGiveOperator(parts[2])
            giveTemp += convertGivePart(parts[1][1])
        }

        return quantity to giveTemp
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
        'W' -> "wonders"

        'O' -> " ou "
        else -> ""
    }
}