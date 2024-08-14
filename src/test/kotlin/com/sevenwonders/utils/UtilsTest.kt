package com.sevenwonders.utils

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.sevenwonders.domain.model.City
import com.sevenwonders.utils.Utils.convertCity
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
        val expectedCity = City(
            name = "alexandria",
            face = 'a',
            wonders = listOf(
                City.Wonder(
                    level = 1,
                    cost = "2 pierre",
                    gives = "3 wonders"
                ),
                City.Wonder(
                    level = 2,
                    cost = "2 or",
                    gives = "1 argile ou 1 or ou 1 bois ou 1 pierre"
                ),
                City.Wonder(
                    level = 3,
                    cost = "2 verre",
                    gives = "7 wonders"
                ),
            )
        )

        val city: City = convertCity(text)

        assertThat(city).isEqualTo(expectedCity)
    }

    @Test
    fun `should create alexandria face b city based on text`() {
        val directory = "/im/cities/city_alexandria_V_b_2A-1BO1PO1GO1A@2B-1VO1TO1R@3P-7W.jpg"
        val directoryPath = getFileOrDirPath(directory)
        val dir = File(directoryPath)
        val text = dir.name
        val expectedCity = City(
            name = "alexandria",
            face = 'b',
            wonders = listOf(
                City.Wonder(
                    level = 1,
                    cost = "2 argile",
                    gives = "1 bois ou 1 pierre ou 1 or ou 1 argile"
                ),
                City.Wonder(
                    level = 2,
                    cost = "2 bois",
                    gives = "1 verre ou 1 tissu ou 1 papier"
                ),
                City.Wonder(
                    level = 3,
                    cost = "3 pierre",
                    gives = "7 wonders"
                ),
            )
        )

        val city: City = convertCity(text)

        assertThat(city).isEqualTo(expectedCity)
    }

    @Test
    fun `should create babylon face a city based on text`() {
        val directory = "/im/cities/city_babylon_A_a_2A-3W@3B-1XO1YO1Z@4A-7W.jpg"
        val directoryPath = getFileOrDirPath(directory)
        val dir = File(directoryPath)
        val text = dir.name
        val expectedCity = City(
            name = "babylon",
            face = 'a',
            wonders = listOf(
                City.Wonder(
                    level = 1,
                    cost = "2 argile",
                    gives = "3 wonders"
                ),
                City.Wonder(
                    level = 2,
                    cost = "3 bois",
                    gives = "1 science tablette ou 1 science compas ou 1 science roue"
                ),
                City.Wonder(
                    level = 3,
                    cost = "4 argile",
                    gives = "7 wonders"
                ),
            )
        )

        val city: City = convertCity(text)

        assertThat(city).isEqualTo(expectedCity)
    }

    @Test
    fun `should create babylon face b city based on text`() {
        val directory = "/im/cities/city_babylon_A_b_1AE1T-3W@2BE1V-2C@3AE1R-1XO1YO1Z.jpg"
        val directoryPath = getFileOrDirPath(directory)
        val dir = File(directoryPath)
        val text = dir.name
        val expectedCity = City(
            name = "babylon",
            face = 'b',
            wonders = listOf(
                City.Wonder(
                    level = 1,
                    cost = "1 argile et 1 tissu",
                    gives = "3 wonders"
                ),
                City.Wonder(
                    level = 2,
                    cost = "2 bois et 1 verre",
                    gives = "2 carte à la fin de l'age"
                ),
                City.Wonder(
                    level = 3,
                    cost = "3 argile et 1 papier",
                    gives = "1 science tablette ou 1 science compas ou 1 science roue"
                ),
            )
        )

        val city: City = convertCity(text)

        assertThat(city).isEqualTo(expectedCity)
    }
}