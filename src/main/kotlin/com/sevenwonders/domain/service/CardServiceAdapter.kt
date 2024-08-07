package com.sevenwonders.domain.service

import com.sevenwonders.api.dto.CardDto
import com.sevenwonders.api.dto.toDto
import com.sevenwonders.api.spi.CardRepository
import com.sevenwonders.domain.repository.CardDB

class CardServiceAdapter : CardRepository {

    private val cardDBService: CardDB = CardDB()

    override suspend fun getAllCards(): List<CardDto> {
        return cardDBService.readAll().map { it.toDto() }
    }
}