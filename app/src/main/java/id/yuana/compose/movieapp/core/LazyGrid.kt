package id.yuana.compose.movieapp.core

import android.os.Parcelable
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import kotlinx.parcelize.Parcelize


inline fun <T : Any> LazyGridScope.items(
    items: LazyPagingItems<T>,
    noinline key: ((item: T) -> Any)? = null,
    noinline span: (LazyGridItemSpanScope.(item: T) -> GridItemSpan)? = null,
    noinline contentType: (item: T) -> Any? = { null },
    crossinline itemContent: @Composable LazyGridItemScope.(item: T?) -> Unit
) = items(
    count = items.itemCount,
    key = if (key == null) null else { index ->
        val item = items.peek(index)
        if (item == null) {
            PagingPlaceholderKey(index)
        } else {
            key(item)
        }
    },
    span = if (span != null) {
        { span(items[it]!!) }
    } else null,
    contentType = { index: Int -> items[index]?.let(contentType) }
) {
    itemContent(items[it])
}

@Parcelize
data class PagingPlaceholderKey(private val index: Int) : Parcelable
