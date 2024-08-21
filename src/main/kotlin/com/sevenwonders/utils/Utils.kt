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
        val cities: MutableList<CityToSave> = mutableListOf()
        val directoryPath = getFileOrDirPath(directory)
        val dir = File(directoryPath)
        val files = dir.listFiles()?.filter { it.isFile }

        if (!files.isNullOrEmpty()) {
            for (file in files) {
                cities.add(convertCityToSave(file.name))
            }
        }
        return cities
    }

    fun convertCityToSave(cityName: String): CityToSave {
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
                cost = convertElement(data[0]),
                gives = convertElement(data[1])
            )
        }
    }

    fun getCardsFromDirectory(dir: File): List<Card> {
        val cards: MutableList<Card> = mutableListOf()
        val files = dir.listFiles()?.filter { it.isFile }

        if (!files.isNullOrEmpty()) {
            for (file in files) {
                val data = file.name.removeSuffix(".jpg").split("_")

                cards.add(
                    Card(
                        age = Age.valueOf(data[0]),
                        color = Color.valueOf(data[1].uppercase()),
                        name = data[2],
                        players = Integer.valueOf(data[3]),
                        cost = data[4],
                        gives = data[5],
                        freeConstructions = data[6]
                    )
                )
            }
        }
        return cards
    }

    private fun convertElement(element: String): String {
        var wc = ""

        element.toCharArray().map { c ->
            when (c) {
                'P' -> wc += "pierre"
                'A' -> wc += "argile"
                'B' -> wc += "bois"
                'G' -> wc += "minerai"
                'V' -> wc += "verre"
                'T' -> wc += "tissu"
                'R' -> wc += "papyrus"
                'O' -> wc += "ou"
                'W' -> wc += "wonders"
                'X' -> wc += "science tablette"
                'Y' -> wc += "science compas"
                'Z' -> wc += "science roue"
                'E' -> wc += "et"
                'M' -> wc += "pièces"
                'C' -> wc += "joue la dernière carte à la fin de l'âge"
                'D' -> wc += "carte gratuite dans la défausse"
                'K' -> wc += "pour"
                'Q' -> wc += "guilde copiée à la fin de la partie"
                'U' -> wc += "bouclier"
                'I' -> wc += "à gauche"
                'J' -> wc += "à droite"
                else -> wc += c
            }
            wc += " "
        }

        return wc.trim()
    }

    private fun convertGivePart(givePart: Char) = when (givePart) {
        'A' -> "argile"
        'P' -> "pierre"
        'G' -> "minerai"
        'B' -> "bois"
        'T' -> "tissu"
        'V' -> "verre"
        'R' -> "papyrus"
        'M' -> "pieces"
        'W' -> "wonders"

        'O' -> " ou "
        else -> ""
    }
}