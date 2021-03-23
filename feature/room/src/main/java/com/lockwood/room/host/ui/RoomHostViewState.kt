package com.lockwood.room.host.ui

import com.lockwood.replicant.state.ViewState
import com.lockwood.room.data.Room

internal data class RoomHostViewState(
  val room: Room,
  val isLoading: Boolean,
) : ViewState {

  companion object {

    val initialStateHost: RoomHostViewState
      @JvmStatic
      get() {
        return RoomHostViewState(room = Room(0, ""), isLoading = true)
      }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as RoomHostViewState

    if (room != other.room) return false
    if (isLoading != other.isLoading) return false

    return true
  }

  override fun hashCode(): Int {
    var result = room.hashCode()
    result = 31 * result + isLoading.hashCode()
    return result
  }

  override fun toString(): String {
    return super.toString()
  }
}
