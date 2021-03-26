package com.lockwood.room.client.ui

import com.lockwood.replicant.state.ViewState
import com.lockwood.room.data.Room

internal data class RoomsDiscoverViewState(
  val rooms: Array<Room>,
  val isLoading: Boolean,
  val isDiscoveryEnd: Boolean,
) : ViewState {

  companion object {

    val initialState: RoomsDiscoverViewState
      @JvmStatic
      get() {
        return RoomsDiscoverViewState(
          rooms = emptyArray(),
          isLoading = true,
          isDiscoveryEnd = false
        )
      }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as RoomsDiscoverViewState

    if (!rooms.contentEquals(other.rooms)) return false
    if (isLoading != other.isLoading) return false
    if (isDiscoveryEnd != other.isDiscoveryEnd) return false

    return true
  }

  override fun hashCode(): Int {
    var result = rooms.contentHashCode()
    result = 31 * result + isLoading.hashCode()
    result = 31 * result + isDiscoveryEnd.hashCode()
    return result
  }

  override fun toString(): String {
    return super.toString()
  }
}
