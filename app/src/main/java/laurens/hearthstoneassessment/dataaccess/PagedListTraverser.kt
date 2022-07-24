package laurens.hearthstoneassessment.dataaccess

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

class PagedListTraverser<T>(private val pagedList: LiveData<PagedList<T>>, var index: Int) {

    @Synchronized
    private fun seekAndGet(offset: Int): T? {
        pagedList.value?.size
        val newIndex = index + offset
        if (validIndex(newIndex))
            index = newIndex
        return current()
    }

    private fun validIndex(index: Int): Boolean {
        val list = pagedList.value ?: return false
        return index >= 0 && index < list.size
    }

    fun next() = seekAndGet(1)
    fun previous() = seekAndGet(-1)

    fun current(): T? {
        pagedList.value?.loadAround(index)
        return pagedList.value?.getOrNull(index)
    }
}
