package com.sevenwonders.utils

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.sevenwonders.domain.model.City
import com.sevenwonders.utils.Utils.convertCity
import org.junit.jupiter.api.Test

internal class UtilsTest {

    @Test
    fun test() {

        assertThat(true).isTrue()
    }

    @Test
    fun `should do something`() {
        val text = "city_alexandria_a_2P-3W@2G-1PO1GO1BO1P@2V-7W.jpg"

        val city: City = convertCity(text)

        assertThat(city.name).isEqualTo("alexandria")
    }
}