package com.sevenwonders.domain.service

import com.sevenwonders.api.spi.CardRepository
import com.sevenwonders.domain.model.Card
import com.sevenwonders.domain.repository.CardDB

class CardServiceAdapter : CardRepository {

    private val cardDBService: CardDB = CardDB()

    override suspend fun getAllCards(): List<Card> {
        return cardDBService.readAll()
    }

    override suspend fun getCardsBy(color: Card.Color): List<Card> {
        return cardDBService.readBy(color)
    }
}