package com.lockwood.room.data.interactor

import com.google.android.gms.tasks.Task
import com.lockwood.automata.core.EMPTY
import com.lockwood.connections.model.EndpointId
import com.lockwood.dwyw.core.wrapper.BuildConfigWrapper
import com.lockwood.player.IPlayerManager
import com.lockwood.room.data.preference.IRoomPreferenceCacheManager
import com.lockwood.room.data.repostiory.IRoomsRepository
import com.lockwood.room.model.Room

internal class RoomsInteractor(
		private val repository: IRoomsRepository,
		private val playerManager: IPlayerManager,
		private val preferenceManager: IRoomPreferenceCacheManager,
		private val buildConfigWrapper: BuildConfigWrapper,
) :
		IRoomsInteractor,
		IPlayerManager by playerManager,
		IRoomsRepository by repository,
		IRoomPreferenceCacheManager by preferenceManager {

	override var isConnected: Boolean
		get() = with(preferenceManager) {
			isConnected && connectedRoom.isValid
		}
		set(value) = with(preferenceManager) {
			isConnected = value
		}

	override fun startAdvertising(name: String): Task<Void> {
		with(preferenceManager) {
			isBroadcasting = true
			sharingRoomName = name
		}
		return repository.startAdvertising(name)
	}

	override fun stopAdvertising() {
		preferenceManager.isBroadcasting = false
		repository.stopAdvertising()
	}

	override fun requestConnection(endpointId: EndpointId): Task<Void> {
		return repository.requestConnection(buildConfigWrapper.deviceModel, endpointId)
	}

	override fun resetCacheState() = with(preferenceManager) {
		isBroadcasting = false
		isConnected = false
		connectedRoom = Room.EMPTY
		sharingRoomName = String.EMPTY
	}

}
