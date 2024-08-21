package com.sevenwonders.utils

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.sevenwonders.domain.model.Card
import com.sevenwonders.domain.model.CityToSave
import com.sevenwonders.utils.Utils.convertCityToSave
import com.sevenwonders.utils.Utils.getCardsFromDirectory
import com.sevenwonders.utils.Utils.getFileOrDirPath
import java.io.File
import org.junit.jupiter.api.Test

internal class UtilsTest {

    @Test
    fun `should create alexandria face a city based on text`() {
        val directory = "/im/cities/city_alexandria_V_a_2P-3W@2G-1AO1GO1BO1P@2V-7W.jpg"
        val directoryPath = getFileOrDirPath(directory)
        val dir = File(directoryPath)
        val text = dir.name
        val expectedCity = CityToSave(
            name = "alexandria",
            resource = "verre",
            face = 'a',
            wonders = "2P-3W@2G-1AO1GO1BO1P@2V-7W"
        )

        val city: CityToSave = convertCityToSave(text)

        assertThat(city).isEqualTo(expectedCity)
    }

    @Test
    fun `should transcript a bleu card with free construction`() {
        val directory = "/im/cards"
        val directoryPath = getFileOrDirPath(directory)
        val dir = File(directoryPath)

        val expectedCard = Card(
            age = Card.Age.I,
            color = Card.Color.BLEU,
            name = "autel",
            players = 3,
            cost = "0",
            gives = "2 wonders",
            freeConstructions = "temple"
        )

        val cards = getCardsFromDirectory(dir)

        assertThat(cards[0]).isEqualTo(expectedCard)
    }

    @Test
    fun `should transcript a bleu card with no free construction`() {
        val directory = "/im/cards"
        val directoryPath = getFileOrDirPath(directory)
        val dir = File(directoryPath)

        val expectedCard = Card(
            age = Card.Age.I,
            color = Card.Color.BLEU,
            name = "prêteur sur gages",
            players = 4,
            cost = "0",
            gives = "3 wonders",
            freeConstructions = ""
        )

        val cards = getCardsFromDirectory(dir)

        assertThat(cards[1]).isEqualTo(expectedCard)
    }
}