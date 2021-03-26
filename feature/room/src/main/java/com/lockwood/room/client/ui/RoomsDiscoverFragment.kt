package com.lockwood.room.client.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.lockwood.automata.android.newFragment
import com.lockwood.automata.recycler.addDividerItemDecoration
import com.lockwood.automata.recycler.applyLayoutManager
import com.lockwood.dwyw.core.ui.BaseFragment
import com.lockwood.replicant.event.observeEvents
import com.lockwood.replicant.ext.observeState
import com.lockwood.replicant.view.ext.requireProgressView
import com.lockwood.replicant.view.ext.setDebouncingOnClickListener
import com.lockwood.room.R
import com.lockwood.room.client.adapter.RoomsAdapter
import com.lockwood.room.data.Room
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.feature.RoomsFeature

internal class RoomsDiscoverFragment : BaseFragment<RoomsDiscoverViewState>() {

  private val viewModel by viewModels<RoomsDiscoverViewModel> {
    getFeature<RoomsFeature>().viewModelsFactory
  }

  private val roomsInteractor: IRoomsInteractor
    get() = getFeature<RoomsFeature>().roomsInteractor

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View = inflater.inflate(R.layout.fragment_rooms_discovery, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initRoomsRecyclerView()
    initStubView()

    with(viewModel) {
      observeState(liveState, ::renderState)
      observeEvents(eventsQueue, ::onEvent)

      startDiscoveryRooms()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    roomsInteractor.stopDiscovery()
  }

  override fun renderState(viewState: RoomsDiscoverViewState) {
    with(viewState) {
      renderLoading(isLoading)
      renderRooms(rooms, isDiscoveryEnd)
    }
  }

  private fun renderLoading(isLoading: Boolean) {
    requireProgressView().updateProgressVisibility(isLoading)
    if (isLoading) {
      requireStubView().isVisible = false
    }
  }

  private fun renderRooms(rooms: Array<Room>, isDiscoveryEnd: Boolean) {
    if (isDiscoveryEnd && rooms.isNullOrEmpty()) {
      requireStubView().isVisible = true
    } else {
      requireRoomsView().adapter = RoomsAdapter(rooms, viewModel::navigateToRoom)
    }
  }

  private fun initRoomsRecyclerView() {
    requireRoomsView().apply {
      applyLayoutManager(RecyclerView.VERTICAL)
      addDividerItemDecoration(RecyclerView.VERTICAL) {
        // TODO: Set White ColorDrawable
        // TODO: Add except last item
      }
    }
  }

  private fun requireRoomsView(): RecyclerView {
    return requireView().findViewById(R.id.rooms_list)
  }

  private fun requireStubView(): View {
    return requireView().findViewById(R.id.rooms_stub)
  }

  private fun initStubView() {
    requireView().findViewById<View>(R.id.start_discovery_button).setDebouncingOnClickListener {
      viewModel.startDiscoveryRooms()
    }
    requireView().findViewById<View>(R.id.start_broadcasting_button).setDebouncingOnClickListener {
      viewModel.navigateToAdvertising()
    }
  }

  companion object {

    @JvmStatic fun newInstance(): RoomsDiscoverFragment = newFragment()
  }
}
