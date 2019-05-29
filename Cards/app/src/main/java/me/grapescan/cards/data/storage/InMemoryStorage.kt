package me.grapescan.cards.data.storage

class InMemoryStorage<T>(defaultValue: T) : Storage<T> {

    private var data: T = defaultValue

    override suspend fun load() = data

    override suspend fun save(data: T) {
        this.data = data
    }
}
