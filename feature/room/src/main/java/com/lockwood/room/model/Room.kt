package com.lockwood.room.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.lockwood.automata.core.EMPTY
import com.lockwood.connections.model.EndpointId

data class Room(
		val endpointId: EndpointId,
		val name: String,
) : Parcelable {

	constructor(parcel: Parcel) : this(
			EndpointId(requireNotNull(parcel.readString())),
			requireNotNull(parcel.readString())
	)

	val isValid: Boolean
		get() = endpointId.toString().isNotEmpty() && name.isNotEmpty()

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Room

		if (endpointId != other.endpointId) return false
		if (name != other.name) return false

		return true
	}

	override fun hashCode(): Int {
		var result = endpointId.toString().hashCode()
		result = 31 * result + name.hashCode()
		return result
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(endpointId.toString())
		parcel.writeString(name)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Creator<Room> {

		val EMPTY: Room
			get() = Room(EndpointId(String.EMPTY), String.EMPTY)

		override fun createFromParcel(parcel: Parcel): Room {
			return Room(parcel)
		}

		override fun newArray(size: Int): Array<Room?> {
			return arrayOfNulls(size)
		}
	}

}
