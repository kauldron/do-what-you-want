package com.lockwood.room.navigation.router

import com.lockwood.replicant.screen.Screen

interface IRoomsRouter {

	fun getScreenToShow(): Screen

}