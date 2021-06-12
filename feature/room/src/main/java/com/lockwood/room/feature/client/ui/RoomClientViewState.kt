package com.lockwood.room.feature.client.ui

import com.lockwood.replicant.state.ViewState
import com.lockwood.room.model.Room

internal data class RoomClientViewState(
    @JvmField
    val room: Room,
    @JvmField
    val isEnabled: Boolean,
    @JvmField
    val isConnected: Boolean,
) : ViewState {

    companion object {

        val initialState: RoomClientViewState
            @JvmStatic
            get() {
                return RoomClientViewState(
                    room = Room.EMPTY,
                    isEnabled = false,
                    isConnected = false
                )
            }
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun toString(): String {
        return super.toString()
    }

    override fun hashCode(): Int {
        var result = room.hashCode()
        result = 31 * result + isEnabled.hashCode()
        result = 31 * result + isConnected.hashCode()
        return result
    }

}
