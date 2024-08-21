package com.sevenwonders.api.spi

import com.sevenwonders.domain.model.City

interface CityRepository {
    suspend fun getAllCities(): List<City>
    suspend fun getCity(name: String): List<City>
    suspend fun getCityBy(face: Char): List<City>
}