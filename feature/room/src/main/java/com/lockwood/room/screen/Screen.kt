package com.lockwood.room.screen

import com.lockwood.replicant.screen.Screen
import com.lockwood.room.data.Room

internal object HostScreen : Screen

internal object RetryErrorScreen : Screen

internal class ConnectionScreen(room: Room) : Screen
