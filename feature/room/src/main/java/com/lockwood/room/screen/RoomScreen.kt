package com.lockwood.room.screen

import com.lockwood.replicant.screen.Screen

interface RoomScreen : Screen

object RoomsDiscoveryScreen : RoomScreen

object RoomsAdvertisingScreen : RoomScreen

object RetryErrorScreen : RoomScreen

class RoomConnectionScreen(val id: Int) : RoomScreen
