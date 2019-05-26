wpackage me.grapescan.cards.api

interface CardRepository {
    suspend fun getCards(): String
}