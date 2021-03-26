package com.lockwood.room.client.ui

import com.lockwood.automata.core.notSafeLazy
import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.connections.callback.DiscoveryCallback
import com.lockwood.dwyw.core.BaseViewModel
import com.lockwood.replicant.event.ShowScreenEvent
import com.lockwood.room.data.Room
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.screen.RetryErrorScreen
import com.lockwood.room.screen.RoomConnectionScreen
import com.lockwood.room.screen.RoomsAdvertisingScreen
import kotlin.random.Random

internal class RoomsDiscoverViewModel(
  private val roomsInteractor: IRoomsInteractor,
  private val connectionsManager: INearbyConnectionsManager
) : BaseViewModel<RoomsDiscoverViewState>(RoomsDiscoverViewState.initialState) {

  private val discoveryCallback: Lazy<DiscoveryCallback> = notSafeLazy {
    object : DiscoveryCallback {
      override fun onEndpointFound() {
        addRoom(Room(Random.nextInt(), Random.nextBytes(4).toString()))
      }

      override fun onEndpointLost() = Unit
    }
  }

  override fun onCleared() {
    connectionsManager.removeDiscoveryCallback(discoveryCallback.value)
    super.onCleared()
  }

  fun startDiscoveryRooms() {
    mutateState { state.copy(isLoading = true, isDiscoveryEnd = false) }

    addDiscoveryCallback()

    // post delay to get around of screen blinking
    postDelay {
      roomsInteractor
        .startDiscovery()
        .addOnCompleteListener {
          // post delay to workaround of getting single endpoint with nearby api
          postDelay { mutateState { state.copy(isLoading = false, isDiscoveryEnd = true) } }
          //          postDelay {
          //            addRoom(Room(Random.nextInt(), Random.nextBytes(4).toString()))
          //            addRoom(Room(Random.nextInt(), Random.nextBytes(4).toString()))
          //          }
        }
        .addOnFailureListener { navigateTo(RetryErrorScreen) }
    }
  }

  fun navigateToAdvertising() {
    offerEvent { ShowScreenEvent(RoomsAdvertisingScreen) }
  }

  fun navigateToRoom(item: Room) {
    navigateTo(RoomConnectionScreen(item.id))
  }

  private fun addDiscoveryCallback() {
    // use lazy to add the discover callback only once
    if (!discoveryCallback.isInitialized()) {
      connectionsManager.addDiscoveryCallback(discoveryCallback.value)
    }
  }

  private fun addRoom(room: Room) {
    val rooms = state.rooms.toMutableSet().apply { add(room) }.toTypedArray()
    mutateState { state.copy(rooms = rooms, isDiscoveryEnd = true) }
  }
}
