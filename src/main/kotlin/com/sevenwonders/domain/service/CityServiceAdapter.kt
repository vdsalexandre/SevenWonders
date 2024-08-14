package com.sevenwonders.domain.service

import com.sevenwonders.api.spi.CityRepository
import com.sevenwonders.domain.model.City
import com.sevenwonders.domain.repository.CityDB

class CityServiceAdapter : CityRepository {

    private val cityDBService: CityDB = CityDB()

    override suspend fun getAllCities(): List<City> {
        return cityDBService.readAll()
    }

    override suspend fun getCity(name: String): List<City> {
        return cityDBService.read(name)
    }
}