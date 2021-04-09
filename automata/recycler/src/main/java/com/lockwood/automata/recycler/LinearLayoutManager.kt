package com.lockwood.automata.recycler

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
