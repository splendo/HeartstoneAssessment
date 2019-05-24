package me.grapescan.cards.data.storage

interface Storage<T> {
    suspend fun load(): T
    suspend fun save(data: T)
}
