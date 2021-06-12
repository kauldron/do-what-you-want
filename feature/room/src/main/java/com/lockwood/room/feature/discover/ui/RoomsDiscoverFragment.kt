package com.lockwood.room.feature.discover.ui

import com.lockwood.dwyw.core.ui.BaseFragment
import com.lockwood.room.feature.RoomsFeature


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
            remember(::renderLoading, isLoading.value, rooms.value.isNullOrEmpty())
            remember(::renderRooms, rooms.value)
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
            setDebouncingOnClickListener(viewModel::startDiscoveryRooms)
        }
        requireView().findViewById<View>(R.id.start_broadcasting_button).apply {
            isVisible = isAudioRecordEnabled
            setDebouncingOnClickListener(viewModel::navigateToAdvertising)
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