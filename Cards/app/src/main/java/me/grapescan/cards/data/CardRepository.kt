package me.grapescan.cards.data

interface CardRepository {
    suspend fun getCards(): List<Card>
}
