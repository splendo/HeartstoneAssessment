package me.grapescan.cards.data.storage

class InMemoryStorage<T>(defaultValue: T) : Storage<T> {

    private var data: T = defaultValue

    override suspend fun getData() = data

    override suspend fun setData(data: T) {
        this.data = data
    }
}
