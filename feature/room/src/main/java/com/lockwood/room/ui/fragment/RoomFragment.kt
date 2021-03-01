package com.lockwood.room.ui.fragment

import android.os.Bundle
import android.view.View
import com.lockwood.replicant.base.ReplicantFragment
import com.lockwood.replicant.ext.lazyViewModel
import com.lockwood.replicant.ext.observe
import com.lockwood.room.data.Room
import com.lockwood.room.data.RoomRepository
import com.lockwood.room.ui.RoomsViewModel
import com.lockwood.room.ui.RoomsViewState

// TODO: Fill RoomFragment
class RoomFragment : ReplicantFragment() {

    private val viewModel: RoomsViewModel by lazyViewModel {
        // TODO: Move RoomRepository to RoomsFeature
        RoomsViewModel(RoomRepository())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observe(viewModel.liveState, ::renderState)

        viewModel.fetchRooms()
    }

    private fun renderState(viewState: RoomsViewState) = with(viewState) {
        renderLoading(isLoading)
        renderRooms(rooms)
    }

    private fun renderRooms(list: List<Room>) {
    }

    private fun renderLoading(loading: Boolean) {
    }

}