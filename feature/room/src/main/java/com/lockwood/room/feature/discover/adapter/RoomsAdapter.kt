package com.lockwood.room.feature.discover.adapter

import android.view.ViewGroup
import com.lockwood.replicant.recycler.ReplicantAdapter
import com.lockwood.replicant.recycler.ReplicantViewHolder
import com.lockwood.replicant.view.listener.ItemClickListener
import com.lockwood.room.R
import com.lockwood.room.model.Room

internal class RoomsAdapter(
    data: List<Room>,
    private val listener: ItemClickListener<Room>,
) : ReplicantAdapter<Room>(data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplicantViewHolder {
        val view = parent.inflate(R.layout.item_view_movie)
        return RoomsViewHolder(data, listener, view)
    }
}
