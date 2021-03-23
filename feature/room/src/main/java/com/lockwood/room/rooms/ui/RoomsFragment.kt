package com.lockwood.room.rooms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lockwood.automata.android.newFragment
import com.lockwood.automata.android.startForegroundService
import com.lockwood.automata.recycler.addDividerItemDecoration
import com.lockwood.automata.recycler.applyLayoutManager
import com.lockwood.dwyw.core.ui.BaseFragment
import com.lockwood.replicant.event.Event
import com.lockwood.replicant.event.observeEvents
import com.lockwood.replicant.ext.lazyViewModel
import com.lockwood.replicant.ext.observeState
import com.lockwood.room.R
import com.lockwood.room.data.Room
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.event.StartClientServiceEvent
import com.lockwood.room.event.StartHostServiceEvent
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.rooms.ui.adapter.RoomsAdapter
import com.lockwood.room.service.ClientForegroundService
import com.lockwood.room.service.HostForegroundService

// TODO: Fill RoomFragment
internal class RoomsFragment : BaseFragment<RoomsViewState>() {

  private val viewModel: RoomsViewModel by lazyViewModel { RoomsViewModel(roomsInteractor) }

  private val roomsInteractor: IRoomsInteractor
    get() = getFeature<RoomsFeature>().roomsInteractor

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View = inflater.inflate(R.layout.fragment_rooms, container, false)

  override fun onEvent(event: Event) {
    when (event) {
      StartClientServiceEvent -> requireContext().startForegroundService<ClientForegroundService>()
      StartHostServiceEvent -> requireContext().startForegroundService<HostForegroundService>()
      else -> super.onEvent(event)
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initRoomsRecyclerView()

    with(viewModel) {
      observeState(liveState, ::renderState)
      observeEvents(eventsQueue, ::onEvent)

      // TODO: Move to host fragment
      startAdvertisingRoom("78")
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    roomsInteractor.stopDiscovery()
  }

  override fun renderState(viewState: RoomsViewState) {
    with(viewState) {
      renderLoading(isLoading)
      renderRooms(rooms)
    }
  }

  private fun renderRooms(rooms: Array<Room>) {
    if (rooms.isNullOrEmpty()) {
      // TODO: Show stub view
    } else {
      requireRoomsView().adapter = RoomsAdapter(rooms, viewModel::navigateToRoom)
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

    @JvmStatic fun newInstance(): RoomsFragment = newFragment()
  }
}
