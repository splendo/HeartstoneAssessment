package me.grapescan.cards.data.storage

interface Storage<T> {
    suspend fun getData(): T
    suspend fun setData(data: T)
}
