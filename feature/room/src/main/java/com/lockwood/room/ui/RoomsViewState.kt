package com.lockwood.room.ui

import com.lockwood.replicant.state.ViewState
import com.lockwood.room.data.Room

data class RoomsViewState(
    val rooms: List<Room>,
    val isLoading: Boolean
) : ViewState {

    companion object {

        val initialState: RoomsViewState
            get() = RoomsViewState(
                rooms = emptyList(),
                isLoading = true
            )
    }
}