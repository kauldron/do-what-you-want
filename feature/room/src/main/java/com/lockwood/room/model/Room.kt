package com.lockwood.room.model

import android.os.Parcelable
import com.lockwood.automata.core.EMPTY
import com.lockwood.connections.model.EndpointId
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
		@JvmField
		val endpointId: EndpointId,
		@JvmField
		val name: String,
) : Parcelable {

	val isValid: Boolean
		get() = endpointId.toString().isNotEmpty() && name.isNotEmpty()

	companion object {

		val EMPTY: Room
			get() = Room(EndpointId(String.EMPTY), String.EMPTY)

	}

}
