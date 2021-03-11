package com.lockwood.room.ui.adapter

import android.view.ViewGroup
import com.lockwood.replicant.base.ReplicantAdapter
import com.lockwood.replicant.base.ReplicantViewHolder
import com.lockwood.replicant.view.listener.ItemClickListener
import com.lockwood.room.R
import com.lockwood.room.data.Room

class RoomsAdapter(
    data: Array<Room>,
    private val listener: ItemClickListener<Room>
) : ReplicantAdapter<Room>(data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplicantViewHolder {
        val view = parent.inflate(R.layout.item_view_movie)
        return RoomsViewHolder(data, listener, view)
    }

}