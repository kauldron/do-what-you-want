package com.lockwood.room.room.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lockwood.automata.android.newFragment
import com.lockwood.dwyw.core.ui.BaseFragment
import com.lockwood.replicant.delegate.intArg
import com.lockwood.replicant.event.observeEvenets
import com.lockwood.replicant.ext.lazyViewModel
import com.lockwood.replicant.ext.observeState
import com.lockwood.room.R
import com.lockwood.room.data.Room
import com.lockwood.room.feature.RoomsFeature

// TODO: Fill RoomDetailFragment
class RoomFragment internal constructor() : BaseFragment<RoomViewState>() {

    private val roomId: Int by intArg(ROOM_ID)

    private val viewModel: RoomViewModel by lazyViewModel {
        RoomViewModel(getFeature<RoomsFeature>().roomsInteractor)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_room, container, false)

    override fun onObserveState() {
        observeState(viewModel.liveState, ::renderState)
        observeEvenets(viewModel.eventsQueue, ::onOnEvent)
    }

    override fun renderState(viewState: RoomViewState) {
        renderRoom(viewState.room)
        renderLoading(viewState.isLoading)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.connectToRoom(roomId)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun renderRoom(room: Room) {
        //  TODO("Not yet implemented")
    }

    companion object {

        private const val ROOM_ID = "room_id"

        @JvmStatic
        fun newInstance(id: Int): RoomFragment = newFragment {
            putInt(ROOM_ID, id)
        }
    }

}