package com.sevenwonders.api.spi

import com.sevenwonders.api.dto.CardDto

interface CardRepository {
    suspend fun getAllCards(): List<CardDto>
}