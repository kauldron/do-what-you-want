package com.lockwood.dwyw.core.screen

import com.lockwood.replicant.screen.Screen

object OnboardingScreen : Screen

object PermissionRequiredScreen : Screen

object RoomsScreen : Screen

class RoomScreen(val id: Int) : Screen
