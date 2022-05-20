package com.kapanen.heartstoneassessment.settings

class AppSettings constructor(storage: Storage) : StoragePropertyDelegate(storage) {
    var isDataInitiallyLoaded: Boolean by default(false)
}
