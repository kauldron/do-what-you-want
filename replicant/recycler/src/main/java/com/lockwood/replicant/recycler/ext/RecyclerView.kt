package com.lockwood.replicant.recycler.ext

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun <V : RecyclerView.ViewHolder> RecyclerView.applyAdapter(
    listAdapter: RecyclerView.Adapter<V>,
) {
    listAdapter.apply {
        setHasStableIds(true)
        setHasFixedSize(true)
    }
    adapter = listAdapter
}

inline fun RecyclerView.applyLayoutManager(
    @RecyclerView.Orientation orientation: Int,
    isHasFixedSize: Boolean = true,
    isNestedScrolling: Boolean = false,
    init: LinearLayoutManager.() -> Unit = {},
) {
    setHasFixedSize(isHasFixedSize)
    isNestedScrollingEnabled = isNestedScrolling
    layoutManager = LinearLayoutManager(context, orientation, false).apply(init)
}

inline fun RecyclerView.addDividerItemDecoration(
    @RecyclerView.Orientation orientation: Int,
    init: DividerItemDecoration.() -> Unit = {},
) {
    val dividerItemDecoration = DividerItemDecoration(context, orientation).apply(init)
    addItemDecoration(dividerItemDecoration)
}
