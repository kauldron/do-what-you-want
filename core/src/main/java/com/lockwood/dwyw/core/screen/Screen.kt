package com.lockwood.dwyw.core.screen

sealed class Screen

object OnboardingScreen : Screen()
object RoomsScreen : Screen()
class RoomScreen(val id: Int) : Screen()
object SettingsScreen : Screen()