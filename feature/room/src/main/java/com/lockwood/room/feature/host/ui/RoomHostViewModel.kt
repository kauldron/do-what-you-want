//package com.lockwood.room.feature.host.ui
//
//internal class RoomHostViewModel(
//    roomsInteractor: IRoomsInteractor,
//    connectionsManager: INearbyConnectionsManager,
//    executorProvider: ExecutorProvider,
//) : BaseConnectionViewModel<RoomHostViewState>(
//    roomsInteractor = roomsInteractor,
//    connectionsManager = connectionsManager,
//    executorProvider = executorProvider,
//    initState = RoomHostViewState.initialState
//) {
//
//    // TODO: Replace with reducer
//    override val stateTransformer: StateTransformer<RoomHostViewState> =
//        object : StateTransformer<RoomHostViewState> {
//            override fun <T> accept(data: T, state: RoomHostViewState): RoomHostViewState {
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
//                offerEvent { ShowAcceptConnectionEvent(room) }
//            }
//
//            override fun onConnectionResult(
//                endpointId: EndpointId,
//                connectionStatus: ConnectionsStatus,
//            ) {
//                offerEvent { MessageEvent("Connection result with $endpointId: $connectionStatus") }
//            }
//
//            override fun onDisconnected(endpointId: EndpointId) {
//                offerEvent { MessageEvent("Some one disconnected from you") }
//            }
//        }
//    }
//
//    override fun onCleared() = with(connectionsManager) {
//        removeConnectionCallback(connectionCallback.value)
//        super.onCleared()
//    }
//
//    fun startBroadcasting() {
//        addConnectionCallback(connectionCallback)
//
//        offerEvent { StartHostServiceEvent }
//        postDelay { offerEvent { RequestCaptureEvent } }
//    }
//
//    fun stopBroadcasting() {
//        connectionsManager.removeConnectionCallback(connectionCallback.value)
//
//        onStopBroadcasting()
//        offerEvent { StopHostServiceEvent }
//    }
//
//    fun onStartBroadcasting() {
//        mutateState { state.copy(isEnabled = true, isSharing = true) }
//    }
//
//    private fun onStopBroadcasting() {
//        mutateState { state.copy(isEnabled = true, isSharing = false) }
//    }
//
//}
