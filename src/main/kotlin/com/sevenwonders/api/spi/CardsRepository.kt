package com.sevenwonders.api.spi

import com.sevenwonders.api.dto.CardDto

interface CardsRepository {
    fun getAllCards(): List<CardDto>
}