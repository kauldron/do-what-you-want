//package com.lockwood.room.feature.discover.ui
//
//
//internal class RoomsDiscoverViewModel(
//    roomsInteractor: IRoomsInteractor,
//    connectionsManager: INearbyConnectionsManager,
//    executorProvider: ExecutorProvider,
//) : BaseConnectionViewModel<RoomsDiscoverViewState>(
//    roomsInteractor = roomsInteractor,
//    connectionsManager = connectionsManager,
//    executorProvider = executorProvider,
//    initState = RoomsDiscoverViewState.initialState
//) {
//
//    override val stateTransformer: StateTransformer<RoomsDiscoverViewState> =
//        RoomsDiscoverStateTransformer()
//
//    private val discoveryCallback: Lazy<DiscoveryCallback> = notSafeLazy {
//        object : DiscoveryCallback {
//            override fun onEndpointFound(endpointId: EndpointId, info: EndpointInfo) {
//                val room = Room(endpointId = endpointId, name = info.name)
//
//                // workaround for duplicate advertising
//                if (roomsInteractor.isSameHostRoom(room)) {
//                    roomsInteractor.stopAdvertising()
//                } else {
//                    addRoom(room)
//                }
//            }
//
//            override fun onEndpointLost(endpointId: EndpointId) {
//                removeRoom(endpointId)
//            }
//        }
//    }
//
//    private val connectionCallback: Lazy<ConnectionCallback> = notSafeLazy {
//        object : ConnectionCallback {
//            override fun onConnectionInitiated(
//                endpointId: EndpointId,
//                connectionInfo: ConnectionInfo,
//            ) {
//                val room = Room(endpointId = endpointId, name = connectionInfo.endpointName)
//
//                withEvent { ShowAcceptConnectionEvent(room) }
//            }
//
//            override fun onConnectionResult(
//                endpointId: EndpointId,
//                connectionStatus: ConnectionsStatus,
//            ) {
//                val statusEvent = when (connectionStatus) {
//                    is ConnectionSuccess -> navigateToConnectedRoom()
//                    else -> MessageEvent("Connection result with $endpointId: $connectionStatus")
//                }
//
//                withEvent { statusEvent }
//            }
//
//            override fun onDisconnected(endpointId: EndpointId) {
//                acceptLoading().withEvent { MessageEvent("Disconnected from $endpointId") }
//            }
//        }
//    }
//
//    override fun onCleared() = with(connectionsManager) {
//        removeDiscoveryCallback(discoveryCallback.value)
//        removeConnectionCallback(connectionCallback.value)
//        super.onCleared()
//    }
//
//    override fun requestConnection(item: Room) {
//        addConnectionCallback(connectionCallback)
//        acceptLoading(true).also { roomsInteractor.connectedRoom = item }
//        super.requestConnection(item)
//    }
//
//    override fun acceptConnection(item: Room) {
//        super.acceptConnection(item)
//        acceptLoading()
//    }
//
//    override fun rejectConnection(item: Room) {
//        super.rejectConnection(item)
//        acceptLoading()
//    }
//
//    fun startDiscoveryRooms() {
//        addDiscoveryCallback(discoveryCallback)
//
//        // post delay to get around of screen blinking
//        acceptLoading(true).also { postDelay(::startDiscovery) }
//    }
//
//    fun navigateToAdvertising() {
//        navigateTo(RoomsAdvertisingScreen)
//    }
//
//    private fun startDiscovery() {
//        roomsInteractor
//            .startDiscovery()
//            .addOnCompleteListener {
//                // post delay to workaround of getting single endpoint with nearby api
//                postDelay { acceptLoading() }
//            }
//    }
//
//    private fun navigateToConnectedRoom(): Event {
//        val connectionScreen = RoomConnectionScreen(roomsInteractor.connectedRoom)
//        return ShowScreenEvent(connectionScreen)
//    }
//
//    private fun addRoom(room: Room) {
//        // TODO: Add action
//        state.rooms.value
//            .toMutableSet()
//            .apply { add(room) }
//            .sortedBy(Room::name)
//            .toTypedArray()
//            .also(::acceptRooms)
//    }
//
//    private fun removeRoom(endpointId: EndpointId) {
//        // TODO: Add action
//        state.rooms.value
//            .toList()
//            .filterNot { room -> room.endpointId == endpointId }
//            .toTypedArray()
//            .also(::acceptRooms)
//    }
//
//    private fun acceptLoading(isLoading: Boolean = false) = mutateState {
//        stateTransformer.accept(LoadingState(isLoading), state)
//    }
//
//    private fun acceptRooms(rooms: Array<Room>) = mutateState {
//        stateTransformer.accept(RoomsArray(rooms), state)
//    }
//
//}
