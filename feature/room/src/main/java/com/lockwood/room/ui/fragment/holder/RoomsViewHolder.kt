package com.lockwood.room.ui.fragment.holder

import android.view.View
import android.widget.TextView
import com.lockwood.replicant.base.ReplicantViewHolder
import com.lockwood.room.R
import com.lockwood.room.data.Room

class RoomsViewHolder(private val data: Array<Room>, view: View) : ReplicantViewHolder(view) {

    private val idView: TextView = view.findViewById(R.id.room_id)
    private val textView: TextView = view.findViewById(R.id.room_text)
    private val statusView: TextView = view.findViewById(R.id.room_status)

    override fun onBind(position: Int) = with(data[position]) {
        idView.text = "ID: $id"
        textView.text = "Name: $name"
        statusView.text = "Is Active: $isActive"
    }

}