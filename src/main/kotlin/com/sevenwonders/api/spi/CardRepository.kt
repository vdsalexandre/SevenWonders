package com.sevenwonders.api.spi

import com.sevenwonders.domain.model.Card

interface CardRepository {
    suspend fun getAllCards(): List<Card>
    suspend fun getCardsBy(color: Card.Color): List<Card>
}