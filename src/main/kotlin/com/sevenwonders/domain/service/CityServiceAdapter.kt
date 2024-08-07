package com.sevenwonders.domain.service

import com.sevenwonders.api.dto.CityDTO
import com.sevenwonders.api.dto.toDto
import com.sevenwonders.api.spi.CityRepository
import com.sevenwonders.domain.repository.CityDB

class CityServiceAdapter : CityRepository {

    private val cityDBService: CityDB = CityDB()

    override suspend fun getAllCities(): List<CityDTO> {
        return cityDBService.readAll().map { it.toDto() }
    }
}