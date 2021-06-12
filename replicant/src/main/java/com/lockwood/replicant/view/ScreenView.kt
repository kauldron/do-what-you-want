package com.lockwood.replicant.view

import com.lockwood.replicant.screen.Screen

interface ScreenView {

    fun goBack()

    fun showScreen(screen: Screen)
}
