package com.lockwood.replicant.event

import com.lockwood.replicant.screen.ErrorScreen
import com.lockwood.replicant.screen.Screen

class GoToBackEvent : Event
class ShowScreenEvent(val screen: Screen) : Event
class ShowErrorScreenEvent(val screen: ErrorScreen) : Event