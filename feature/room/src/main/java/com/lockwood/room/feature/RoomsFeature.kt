package com.lockwood.room.feature

import androidx.lifecycle.ViewModelProvider
import com.lockwood.automata.core.notSafeLazy
import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.dwyw.core.wrapper.BuildConfigWrapper
import com.lockwood.player.IPlayerManager
import com.lockwood.replicant.cache.preference.IPreferenceCacheManager
import com.lockwood.replicant.cache.preference.PreferenceCacheManager
import com.lockwood.replicant.context.ApplicationContextProvider
import com.lockwood.replicant.executor.ExecutorProvider
import com.lockwood.replicant.feature.Feature
import com.lockwood.room.RoomViewModelsFactory
import com.lockwood.room.data.interactor.IRoomsInteractor
import com.lockwood.room.data.interactor.RoomsInteractor
import com.lockwood.room.data.preference.IRoomPreferenceCacheManager
import com.lockwood.room.data.preference.RoomPreferenceCacheManager
import com.lockwood.room.data.repostiory.IRoomsRepository
import com.lockwood.room.data.repostiory.RoomsRepository
import com.lockwood.room.navigation.launcher.IRoomsLauncher
import com.lockwood.room.navigation.launcher.RoomsLauncher
import com.lockwood.room.navigation.launcher.license.IThirdPartyLicenseLauncher
import com.lockwood.room.navigation.launcher.license.ThirdPartyLicenseLauncher
import com.lockwood.room.navigation.router.IRoomsRouter
import com.lockwood.room.navigation.router.RoomsRouter

class RoomsFeature(
		contextProvider: ApplicationContextProvider,
		connectionsManager: INearbyConnectionsManager,
		playerManager: IPlayerManager,
		buildConfigWrapper: BuildConfigWrapper,
		executorProvider: ExecutorProvider
) : Feature {

	val roomsInteractor: IRoomsInteractor by lazy {
		RoomsInteractor(roomsRepository, playerManager, roomPreferenceCacheManager, buildConfigWrapper)
	}

	val viewModelsFactory: ViewModelProvider.Factory by notSafeLazy {
		RoomViewModelsFactory(roomsInteractor, connectionsManager, executorProvider)
	}

	private val preferenceCacheManager: IPreferenceCacheManager by notSafeLazy {
		PreferenceCacheManager(contextProvider)
	}

	private val roomPreferenceCacheManager: IRoomPreferenceCacheManager by notSafeLazy {
		RoomPreferenceCacheManager(preferenceCacheManager)
	}

	private val roomsRepository: IRoomsRepository by notSafeLazy {
		RoomsRepository(connectionsManager)
	}

	val roomsLauncher: IRoomsLauncher
		get() = RoomsLauncher()

	val thirdPartyLicenseLauncher: IThirdPartyLicenseLauncher
		get() = ThirdPartyLicenseLauncher()

	val roomsRouter: IRoomsRouter
		get() = RoomsRouter(roomsInteractor)

}
