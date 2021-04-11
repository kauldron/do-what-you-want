package com.lockwood.room.feature.discover.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.lockwood.automata.android.newFragment
import com.lockwood.automata.recycler.addDividerItemDecoration
import com.lockwood.automata.recycler.applyLayoutManager
import com.lockwood.dwyw.core.ui.BaseFragment
import com.lockwood.dwyw.ui.core.Colors
import com.lockwood.recorder.feature.RecorderFeature
import com.lockwood.replicant.event.Event
import com.lockwood.replicant.event.observeEvents
import com.lockwood.replicant.ext.observeState
import com.lockwood.replicant.view.ext.requireProgressView
import com.lockwood.replicant.view.ext.setDebouncingOnClickListener
import com.lockwood.room.R
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.feature.discover.adapter.RoomsAdapter
import com.lockwood.room.feature.discover.event.ShowAcceptConnectionEvent
import com.lockwood.room.model.Room

internal class RoomsDiscoverFragment : BaseFragment<RoomsDiscoverViewState>() {

	private val viewModel by viewModels<RoomsDiscoverViewModel> {
		getFeature<RoomsFeature>().viewModelsFactory
	}

	private val roomsInteractor: IRoomsInteractor
		get() = getFeature<RoomsFeature>().roomsInteractor

	private val isAudioRecordEnabled: Boolean
		get() = getFeature<RecorderFeature>().isEnabled

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
		roomsInteractor.stopDiscovery()
		super.onDestroyView()
	}

	override fun onEvent(event: Event) = when (event) {
		is ShowAcceptConnectionEvent -> showConnectionDialog(event.room)
		else -> super.onEvent(event)
	}

	override fun renderState(viewState: RoomsDiscoverViewState) {
		with(viewState) {
			renderLoading(isLoading, rooms.isNullOrEmpty())
			renderRooms(rooms)
		}
	}

	private fun renderLoading(isLoading: Boolean, isRoomsEmpty: Boolean) {
		requireProgressView().updateProgressVisibility(isLoading)
		requireStubView().isVisible = !isLoading && isRoomsEmpty
		requireRoomsView().isVisible = !isLoading && !isRoomsEmpty
	}

	private fun renderRooms(rooms: Array<Room>) {
		requireRoomsView().adapter = if (rooms.isNullOrEmpty()) {
			null
		} else {
			RoomsAdapter(rooms, viewModel::requestConnection)
		}
	}

	private fun showConnectionDialog(room: Room) = showDialog {
		setTitle("Attention")
		setMessage("Do you want to connect with ${room.name}?")
		setNegativeButton("No") { dialog, _ ->
			viewModel.rejectConnection(room)
			dialog.dismiss()
		}
		setPositiveButton("Yes") { _, _ ->
			viewModel.acceptConnection(room)
		}
	}

	private fun initRoomsRecyclerView() = requireRoomsView().apply {
		applyLayoutManager(RecyclerView.VERTICAL)
		addDividerItemDecoration(RecyclerView.VERTICAL) {
			DrawableCompat.setTint(requireNotNull(drawable), Colors.PURPLE)
		}
	}

	private fun initStubView() {
		requireView().findViewById<View>(R.id.start_discovery_button).apply {
			setDebouncingOnClickListener { viewModel.startDiscoveryRooms() }
		}
		requireView().findViewById<View>(R.id.start_broadcasting_button).apply {
			isVisible = isAudioRecordEnabled
			setDebouncingOnClickListener { viewModel.navigateToAdvertising() }
		}
	}

	private fun requireRoomsView(): RecyclerView {
		return requireView().findViewById(R.id.rooms_list)
	}

	private fun requireStubView(): View {
		return requireView().findViewById(R.id.rooms_stub)
	}

	companion object {

		@JvmStatic
		fun newInstance(): RoomsDiscoverFragment = newFragment()
	}
}
