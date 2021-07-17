//package com.lockwood.room.feature.client.ui
//
//internal class RoomClientViewModel(
//    roomsInteractor: IRoomsInteractor,
//    connectionsManager: INearbyConnectionsManager,
//    executorProvider: ExecutorProvider,
//) : BaseConnectionViewModel<RoomClientViewState>(
//    roomsInteractor = roomsInteractor,
//    connectionsManager = connectionsManager,
//    executorProvider = executorProvider,
//    initState = RoomClientViewState.initialState
//) {
//
//    override val stateTransformer: StateTransformer<RoomClientViewState> =
//        object : StateTransformer<RoomClientViewState> {
//            override fun <T> accept(data: T, state: RoomClientViewState): RoomClientViewState {
//                // TODO: Replace with reducer
//                return state
//            }
//        }
//
//    private val connectionCallback: Lazy<ConnectionCallback> = notSafeLazy {
//        object : ConnectionCallback {
//            override fun onConnectionInitiated(
//                endpointId: EndpointId,
//                connectionInfo: ConnectionInfo,
//            ) {
//                val room = Room(endpointId = endpointId, name = connectionInfo.endpointName)
//
//                acceptConnection(room)
//            }
//
//            override fun onConnectionResult(
//                endpointId: EndpointId,
//                connectionStatus: ConnectionsStatus,
//            ) = when (connectionStatus) {
//                is ConnectionSuccess -> onConnected()
//                else -> offerEvent { MessageEvent("Connection result with $endpointId: $connectionStatus") }
//            }
//
//            override fun onDisconnected(endpointId: EndpointId) {
//                stopPlaying()
//
//                offerEvent { MessageEvent("Disconnected from $endpointId") }
//            }
//        }
//    }
//
//    override fun onCleared() = with(connectionsManager) {
//        removeConnectionCallback(connectionCallback.value)
//        super.onCleared()
//    }
//
//    override fun requestConnection(item: Room) {
//        addConnectionCallback(connectionCallback)
//
//        super.requestConnection(item)
//    }
//
//    fun requestConnection() {
//        requestConnection(roomsInteractor.connectedRoom)
//    }
//
//    fun onConnected() {
//        roomsInteractor.isConnected = true
//
//        startPlaying()
//    }
//
//    fun onDisconnect() {
//        with(roomsInteractor) {
//            isConnected = false
//            connectionsManager.disconnectFromEndpoint(connectedRoom.endpointId)
//        }
//
//        stopPlaying()
//    }
//
//    private fun startPlaying() {
//        mutateState {
//            state.copy(
//                isEnabled = true,
//                isConnected = true,
//                room = roomsInteractor.connectedRoom
//            )
//        }
//        offerEvent { StartClientServiceEvent }
//    }
//
//    private fun stopPlaying() {
//        mutateState { state.copy(isEnabled = true, isConnected = false) }
//        offerEvent { StopClientServiceEvent }
//    }
//
//}
