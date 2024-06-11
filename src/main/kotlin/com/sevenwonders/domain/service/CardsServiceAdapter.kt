package com.sevenwonders.domain.service

import com.sevenwonders.api.dto.CardDto
import com.sevenwonders.api.spi.CardsRepository

class CardsServiceAdapter : CardsRepository {
    override fun getAllCards(): List<CardDto> {
        TODO("Not yet implemented")
    }
}