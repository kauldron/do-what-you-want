package com.lockwood.room.feature.discover

import com.lockwood.replicant.screen.Screen
import com.lockwood.room.model.Room

internal sealed class RoomsDiscoveryEffects {

	class ShowAcceptConnection(@JvmField val room: Room) : RoomsDiscoveryEffects()
	class ShowScreen(@JvmField val screen: Screen) : RoomsDiscoveryEffects()
}