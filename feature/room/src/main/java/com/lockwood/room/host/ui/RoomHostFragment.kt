package com.lockwood.room.host.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lockwood.automata.android.newFragment
import com.lockwood.automata.android.startForegroundService
import com.lockwood.dwyw.core.ui.BaseFragment
import com.lockwood.replicant.delegate.intArg
import com.lockwood.replicant.event.Event
import com.lockwood.replicant.event.observeEvents
import com.lockwood.replicant.ext.lazyViewModel
import com.lockwood.replicant.ext.observeState
import com.lockwood.replicant.view.ext.requireProgressView
import com.lockwood.room.R
import com.lockwood.room.data.Room
import com.lockwood.room.event.StartHostServiceEvent
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.service.HostForegroundService

// TODO: Fill RoomDetailFragment
internal class RoomHostFragment : BaseFragment<RoomHostViewState>() {

  private val roomId: Int by intArg(ROOM_ID)

  private val hostViewModel: RoomHostViewModel by lazyViewModel {
    RoomHostViewModel(getFeature<RoomsFeature>().roomsInteractor)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View = inflater.inflate(R.layout.fragment_room, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    with(hostViewModel) {
      observeState(liveState, ::renderState)
      observeEvents(eventsQueue, ::onEvent)

      //      startAdvertisingRoom()
    }
  }

  override fun onEvent(event: Event) {
    when (event) {
      StartHostServiceEvent -> requireContext().startForegroundService<HostForegroundService>()
      else -> super.onEvent(event)
    }
  }

  override fun renderState(viewState: RoomHostViewState) {
    with(viewState) {
      renderRoom(room)
      renderLoading(isLoading)
    }
  }

  private fun renderLoading(isLoading: Boolean) {
    requireProgressView().updateProgressVisibility(isLoading)
  }

  private fun renderRoom(room: Room) {
    //  TODO("Not yet implemented")
  }

  companion object {

    private const val ROOM_ID = "room_id"

    @JvmStatic fun newInstance(id: Int): RoomHostFragment = newFragment { putInt(ROOM_ID, id) }
  }
}
