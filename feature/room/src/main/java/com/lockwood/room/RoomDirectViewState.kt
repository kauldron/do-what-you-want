package com.lockwood.room

import com.lockwood.replicant.state.ViewState
import com.lockwood.room.DirectState.INITIALIZING

// TODO: Remove data and implement copy for states
internal data class RoomDirectViewState(
  val directState: DirectState,
  val isLoading: Boolean,
) : ViewState {

  companion object {

    val initialState: RoomDirectViewState
      @JvmStatic get() = RoomDirectViewState(directState = INITIALIZING, isLoading = true)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as RoomDirectViewState

    if (directState != other.directState) return false
    if (isLoading != other.isLoading) return false

    return true
  }

  override fun hashCode(): Int {
    var result = directState.hashCode()
    result = 31 * result + isLoading.hashCode()
    return result
  }

  override fun toString(): String {
    return super.toString()
  }
}

internal enum class DirectState {
  INITIALIZING,
  REQUEST_SERVICES,
  DISCOVER_SERVICES,
  ADD_LOCAL_SERVICES,
}
