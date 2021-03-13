package com.lockwood.room.rooms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lockwood.automata.android.newFragment
import com.lockwood.automata.recycler.addDividerItemDecoration
import com.lockwood.automata.recycler.applyLayoutManager
import com.lockwood.dwyw.core.ui.BaseFragment
import com.lockwood.replicant.event.observeEvenets
import com.lockwood.replicant.ext.lazyViewModel
import com.lockwood.replicant.ext.observeState
import com.lockwood.replicant.view.listener.ItemClickListener
import com.lockwood.room.R
import com.lockwood.room.data.Room
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.rooms.ui.adapter.RoomsAdapter
import timber.log.Timber

// TODO: Fill RoomFragment
class RoomsFragment internal constructor(): BaseFragment<RoomsViewState>(), ItemClickListener<Room> {

    init {
        Timber.d("RoomsFragment crated")
    }

    private val viewModel: RoomsViewModel by lazyViewModel {
        RoomsViewModel(getFeature<RoomsFeature>().roomsInteractor)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_rooms, container, false)

    override fun onBeforeObserveState() {
        initRoomsRecyclerView()
    }

    override fun onObserveState() {
        observeState(viewModel.liveState, ::renderState)
        observeEvenets(viewModel.eventsQueue, ::onOnEvent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("RoomsFragment fetchRooms")
        viewModel.fetchRooms()
    }

    override fun onClick(item: Room) {
        viewModel.navigateToRoom(item)
    }

    override fun renderState(viewState: RoomsViewState) = with(viewState) {
        renderLoading(isLoading)
        renderRooms(rooms)
    }

    private fun renderRooms(rooms: Array<Room>) {
        if (rooms.isNullOrEmpty()) {
            // TODO: Show stub view
        } else {
            requireRoomsView().adapter = RoomsAdapter(rooms, this)
        }
    }

    private fun initRoomsRecyclerView() {
        requireRoomsView().apply {
            applyLayoutManager(RecyclerView.VERTICAL)
            addDividerItemDecoration(RecyclerView.VERTICAL) {
                // TODO: Get resource manager
                // TODO: Set White ColorDrawable
                // TODO: Add except last item
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