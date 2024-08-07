package com.sevenwonders.api.spi

import com.sevenwonders.api.dto.CityDTO

interface CityRepository {
    suspend fun getAllCities(): List<CityDTO>
}