package com.lockwood.dwyw.core.ui.effects

import com.lockwood.replicant.screen.Screen

sealed class Effects {
    class MessageEffect(@JvmField val message: String) : Effects()
    class ErrorMessageEffect(@JvmField val message: String) : Effects()
    open class ShowScreenEffect(@JvmField val screen: Screen) : Effects()
    object GoToBackEffect : Effects()
}
