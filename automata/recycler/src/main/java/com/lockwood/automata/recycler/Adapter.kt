package com.lockwood.automata.recycler

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
