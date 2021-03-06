package com.lockwood.room.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lockwood.automata.android.newFragment
import com.lockwood.dwyw.core.ui.BaseFragment
import com.lockwood.replicant.ext.lazyViewModel
import com.lockwood.replicant.ext.observe
import com.lockwood.replicant.view.ext.requireProgressView
import com.lockwood.room.R
import com.lockwood.room.data.Room
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.ui.RoomsViewModel
import com.lockwood.room.ui.RoomsViewState

// TODO: Fill RoomFragment
class RoomsFragment : BaseFragment() {

    private val viewModel: RoomsViewModel by lazyViewModel {
        RoomsViewModel(getFeature<RoomsFeature>().roomRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_rooms, container, false)

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

    private fun renderLoading(isLoading: Boolean) {
        requireProgressView().updateProgressVisibility(isLoading)
    }

    companion object {

        @JvmStatic
        fun newInstance(): RoomsFragment = newFragment()
    }

}