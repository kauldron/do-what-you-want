package com.lockwood.room.feature.discover.adapter

import android.view.View
import android.widget.TextView
import com.lockwood.replicant.base.ReplicantViewHolder
import com.lockwood.replicant.view.listener.ItemClickListener
import com.lockwood.replicant.view.listener.setDebouncingOnClickListener
import com.lockwood.room.R
import com.lockwood.room.model.Room

internal class RoomsViewHolder(
		@JvmField
		private val data: List<Room>,
		@JvmField
		private val listener: ItemClickListener<Room>,
		itemView: View,
) : ReplicantViewHolder(itemView) {

	private val idView: TextView
		get() = itemView.findViewById(R.id.room_id)

	private val textView: TextView
		get() = itemView.findViewById(R.id.room_text)

	override fun onBind(position: Int) {
		with(data[position]) {
			idView.text = endpointId.toString()
			textView.text = name

			itemView.setDebouncingOnClickListener { listener.invoke(this) }
		}
	}
}
