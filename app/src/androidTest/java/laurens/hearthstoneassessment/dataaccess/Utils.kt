package laurens.hearthstoneassessment.dataaccess

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun <T> LiveData<PagedList<T>>.awaitFirstData(): List<T> {
    return suspendCancellableCoroutine { continuation ->
        observeForever { nullable ->
            nullable?.also {
                continuation.resume(it.snapshot())
            }
        }
    }
}

suspend fun <T> DataSource.Factory<Int, T>.awaitFirstData(): List<T> {
    val pagedList = LivePagedListBuilder(this, 10).build()
    return pagedList.awaitFirstData()
}

suspend fun <T> LiveData<T>.await(): T? {
    return suspendCancellableCoroutine { continuation ->
        this.observeForever(object : Observer<T> {
            override fun onChanged(t: T?) {
                this@await.removeObserver(this)
                continuation.resume(t)
            }
        })

    }
}
