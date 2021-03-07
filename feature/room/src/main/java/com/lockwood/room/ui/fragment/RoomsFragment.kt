package com.lockwood.room.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lockwood.automata.android.newFragment
import com.lockwood.automata.recycler.addDividerItemDecoration
import com.lockwood.automata.recycler.applyLayoutManager
import com.lockwood.dwyw.core.ui.BaseFragment
import com.lockwood.replicant.ext.lazyViewModel
import com.lockwood.replicant.ext.observe
import com.lockwood.replicant.view.ext.requireProgressView
import com.lockwood.room.R
import com.lockwood.room.data.Room
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.ui.RoomsViewModel
import com.lockwood.room.ui.RoomsViewState
import com.lockwood.room.ui.fragment.holder.RoomsAdapter

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
        setupViews()

        observe(viewModel.liveState, ::renderState)

        viewModel.fetchRooms()
    }

    private fun renderState(viewState: RoomsViewState) = with(viewState) {
        renderLoading(isLoading)
        renderRooms(rooms)
    }

    private fun renderRooms(rooms: Array<Room>) {
        if (rooms.isNullOrEmpty()) {
            // TODO: Show stub view
        } else {
            requireRoomsView().adapter = RoomsAdapter(rooms)
        }
    }

    private fun renderLoading(isLoading: Boolean) {
        requireProgressView().updateProgressVisibility(isLoading)
    }

    private fun setupViews() {
        requireRoomsView().apply {
            applyLayoutManager(RecyclerView.VERTICAL)
            addDividerItemDecoration(RecyclerView.VERTICAL) {
                // TODO: Get resource manager
                // TODO: Set White ColorDrawable
            }
        }
    }

    private fun requireRoomsView(): RecyclerView {
        return requireView().findViewById(R.id.rooms_list)
    }

    companion object {

        @JvmStatic
        fun newInstance(): RoomsFragment = newFragment()
    }

}