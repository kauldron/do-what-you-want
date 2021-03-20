package com.lockwood.room.screen

import com.lockwood.direct.WifiP2pError
import com.lockwood.replicant.screen.ErrorScreen

internal object DirectNotAvailableScreen : ErrorScreen

internal class P2pErrorScreen(val error: WifiP2pError) : ErrorScreen
