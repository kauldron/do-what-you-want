package com.lockwood.room.rooms.ui

import com.lockwood.replicant.state.ViewState
import com.lockwood.room.data.Room

internal data class RoomsViewState(
  val rooms: Array<Room>,
  val isLoading: Boolean,
) : ViewState {

  companion object {

    val initialState: RoomsViewState
      @JvmStatic get() = RoomsViewState(rooms = emptyArray(), isLoading = true)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as RoomsViewState

    if (!rooms.contentEquals(other.rooms)) return false
    if (isLoading != other.isLoading) return false

    return true
  }

  override fun hashCode(): Int {
    var result = rooms.contentHashCode()
    result = 31 * result + isLoading.hashCode()
    return result
  }

  override fun toString(): String {
    return super.toString()
  }
}
