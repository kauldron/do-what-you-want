package com.lockwood.replicant.screen

sealed class Screen

object LoginScreen : Screen()
object MainScreen : Screen()
object SettingsScreen : Screen()