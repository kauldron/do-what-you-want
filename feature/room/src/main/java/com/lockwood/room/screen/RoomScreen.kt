package com.lockwood.room.screen

import com.lockwood.replicant.screen.Screen
import com.lockwood.room.model.Room

interface RoomScreen : Screen

object DiscoveryScreen : RoomScreen
object AdvertisingScreen : RoomScreen
class ConnectionScreen(@JvmField val room: Room) : RoomScreen
