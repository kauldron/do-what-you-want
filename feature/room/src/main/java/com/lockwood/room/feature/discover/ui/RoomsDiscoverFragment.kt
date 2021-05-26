package com.lockwood.room.feature.discover.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.lockwood.automata.android.newFragment
import com.lockwood.automata.recycler.addDividerItemDecoration
import com.lockwood.automata.recycler.applyLayoutManager
import com.lockwood.connections.feature.ConnectionsFeature
import com.lockwood.dwyw.core.ui.BaseFragment
import com.lockwood.dwyw.core.ui.state.LoadingState
import com.lockwood.dwyw.core.ui.state.LoadingState.Content
import com.lockwood.dwyw.core.ui.state.LoadingState.Loading
import com.lockwood.dwyw.core.ui.state.LoadingState.Stub
import com.lockwood.dwyw.ui.core.Colors
import com.lockwood.recorder.feature.RecorderFeature
import com.lockwood.replicant.view.ext.requireProgressView
import com.lockwood.replicant.view.ext.requireScreenView
import com.lockwood.replicant.view.listener.setDebouncingOnClickListener
import com.lockwood.room.R
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.feature.discover.RoomsDiscoverViewState
import com.lockwood.room.feature.discover.RoomsDiscoveryAction
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.AcceptConnection
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.DiscoverRooms
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.NavigateToScreen
import com.lockwood.room.feature.discover.RoomsDiscoveryAction.RejectConnection
import com.lockwood.room.feature.discover.RoomsDiscoveryEffects
import com.lockwood.room.feature.discover.RoomsDiscoveryEffects.ShowAcceptConnection
import com.lockwood.room.feature.discover.RoomsDiscoveryEffects.ShowScreen
import com.lockwood.room.feature.discover.RoomsDiscoveryStore
import com.lockwood.room.feature.discover.adapter.RoomsAdapter
import com.lockwood.room.model.Room
import com.lockwood.room.screen.AdvertisingScreen

internal class RoomsDiscoverFragment : BaseFragment<RoomsDiscoveryStore>() {

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
		super.onViewCreated(view, savedInstanceState)
		initRoomsRecyclerView()
		initStubView()

		with(store) {
			listenEffect(::handleEffect)
			listenState(::renderState)

			accept(DiscoverRooms)
		}
	}

	override fun onDestroyView() {
		roomsInteractor.stopDiscovery()
		super.onDestroyView()
	}

	override fun onSaveInstanceState(outState: Bundle) = with(outState) {
		super.onSaveInstanceState(this)
		RoomsDiscoverViewState.toBundle(this, store.currentState)
	}

	override fun initStore(savedInstanceState: Bundle?): RoomsDiscoveryStore {
		val state = RoomsDiscoverViewState.fromBundleOrDefault(savedInstanceState)

		val roomsInteractor = getFeature<RoomsFeature>().roomsInteractor
		val connectionsManager = getFeature<ConnectionsFeature>().nearbyConnectionsManager

		return RoomsDiscoveryStore(roomsInteractor, connectionsManager, state)
	}

	private fun renderState(viewState: RoomsDiscoverViewState) = with(viewState) {
		remember(::renderLoading, loadingState)
		remember(::renderRooms, rooms)
	}

	private fun handleEffect(effect: RoomsDiscoveryEffects) = when (effect) {
		is ShowAcceptConnection -> showConnectionDialog(effect.room)
		is ShowScreen -> requireScreenView().showScreen(effect.screen)
	}

	private fun renderLoading(loadingState: LoadingState) = when (loadingState) {
		is Loading -> renderLoading(isLoading = true)
		is Content -> renderLoading(isShowRooms = true)
		is Stub -> renderLoading(isStub = true)
	}

	private fun renderLoading(
			isLoading: Boolean = false,
			isStub: Boolean = false,
			isShowRooms: Boolean = false
	) {
		requireProgressView().updateProgressVisibility(isLoading)
		requireStubView().isVisible = isStub
		requireRoomsView().isVisible = isShowRooms
	}

	private fun renderRooms(rooms: List<Room>) {
		requireRoomsView().adapter = if (rooms.isNullOrEmpty()) {
			null
		} else {
			RoomsAdapter(rooms) { clickedRoom ->
				store.accept(RoomsDiscoveryAction.RequestConnection(clickedRoom))
			}
		}
	}

	private fun showConnectionDialog(room: Room) = with(store) {
		showDialog {
			setTitle("Attention")
			setMessage("Do you want to connect with ${room.name}?")
			setNegativeButton("No") { dialog, _ -> accept(RejectConnection(room)).also { dialog.dismiss() } }
			setPositiveButton("Yes") { _, _ -> accept(AcceptConnection(room)) }
		}
	}

	private fun initRoomsRecyclerView() = requireRoomsView().apply {
		applyLayoutManager(RecyclerView.VERTICAL)
		addDividerItemDecoration(RecyclerView.VERTICAL) {
			requireNotNull(drawable).setTint(Colors.PURPLE)
		}
	}

	private fun initStubView() {
		requireView().findViewById<View>(R.id.start_discovery_button).apply {
			setDebouncingOnClickListener { store.accept(DiscoverRooms) }
		}
		requireView().findViewById<View>(R.id.start_broadcasting_button).apply {
			isVisible = isAudioRecordEnabled
			setDebouncingOnClickListener { store.accept(NavigateToScreen(AdvertisingScreen)) }
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