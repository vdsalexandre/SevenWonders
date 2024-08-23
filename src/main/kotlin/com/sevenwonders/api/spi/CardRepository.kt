package com.sevenwonders.api.spi

import com.sevenwonders.domain.model.Card

interface CardRepository {
    suspend fun getAllCards(): List<Card>
    suspend fun getCardsBy(color: Card.Color): List<Card>
    suspend fun getCardsByPlayers(players: Int): List<Card>
    suspend fun getCardsByAge(age: Card.Age): List<Card>
    suspend fun getCardsByAgeAndPlayers(age: Card.Age, players: Int): List<Card>
}