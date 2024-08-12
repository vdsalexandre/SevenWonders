package com.sevenwonders.api.spi

import com.sevenwonders.domain.model.City

interface CityRepository {
    suspend fun getAllCities(): List<City>
}