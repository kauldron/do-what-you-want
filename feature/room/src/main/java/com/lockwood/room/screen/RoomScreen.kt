package com.lockwood.room.screen

import com.lockwood.replicant.screen.Screen
import com.lockwood.room.model.Room

interface RoomScreen : Screen

object RoomsDiscoveryScreen : RoomScreen

object RoomsAdvertisingScreen : RoomScreen

class RoomConnectionScreen(val room: Room) : RoomScreen
