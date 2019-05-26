package me.grapescan.cards.api

object Injector {
    val cardRepository: CardRepository by lazy { JsonCardRepository() }
}