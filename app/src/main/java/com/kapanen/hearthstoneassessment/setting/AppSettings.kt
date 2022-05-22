package com.kapanen.hearthstoneassessment.setting

class AppSettings constructor(storage: Storage) : StoragePropertyDelegate(storage) {
    var isDataInitiallyLoaded: Boolean by default(false)
}
