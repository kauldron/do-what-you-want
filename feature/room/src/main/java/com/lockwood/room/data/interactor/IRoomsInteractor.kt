package com.lockwood.room.data.interactor

import com.google.android.gms.tasks.Task
import com.lockwood.connections.model.EndpointId
import com.lockwood.room.data.preference.IRoomPreferenceCacheManager
import com.lockwood.room.data.repostiory.IRoomsRepository

interface IRoomsInteractor : IRoomsRepository, IRoomPreferenceCacheManager {

	fun requestConnection(endpointId: EndpointId): Task<Void>

	fun resetCacheState()

}
