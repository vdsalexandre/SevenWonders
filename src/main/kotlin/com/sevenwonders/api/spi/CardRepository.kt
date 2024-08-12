package com.sevenwonders.api.spi

import com.sevenwonders.domain.model.Card

interface CardRepository {
    suspend fun getAllCards(): List<Card>
}