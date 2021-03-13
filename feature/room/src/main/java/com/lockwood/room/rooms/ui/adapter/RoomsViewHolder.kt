package com.lockwood.room.rooms.ui.adapter

import android.view.View
import android.widget.TextView
import com.lockwood.replicant.base.ReplicantViewHolder
import com.lockwood.replicant.view.listener.ItemClickListener
import com.lockwood.room.R
import com.lockwood.room.data.Room

class RoomsViewHolder(
    private val data: Array<Room>,
    private val listener: ItemClickListener<Room>,
    itemView: View,
) : ReplicantViewHolder(itemView) {

    private val idView: TextView
        get() = itemView.findViewById(R.id.room_id)
    private val textView: TextView
        get() = itemView.findViewById(R.id.room_text)
    private val statusView: TextView
        get() = itemView.findViewById(R.id.room_status)

    override fun onBind(position: Int) = with(data[position]) {
        idView.text = "ID: $id"
        textView.text = "Name: $name"
        statusView.text = "Is Active: $isActive"

        itemView.setOnClickListener { listener.onClick(this) }
    }

}