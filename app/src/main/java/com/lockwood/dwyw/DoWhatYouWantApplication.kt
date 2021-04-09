package com.lockwood.dwyw

import com.lockwood.connections.feature.ConnectionsToolsProvider
import com.lockwood.dwyw.core.feature.CoreToolsProvider
import com.lockwood.dwyw.core.wrapper.BuildConfigWrapperToolsProvider
import com.lockwood.player.feature.PlayerToolsProvider
import com.lockwood.recorder.feature.RecorderToolsProvider
import com.lockwood.replicant.context.ApplicationContextProvider
import com.lockwood.room.feature.RoomToolsProvider

interface DoWhatYouWantApplication :
		ApplicationContextProvider,
		CoreToolsProvider,
		ConnectionsToolsProvider,
		RoomToolsProvider,
		BuildConfigWrapperToolsProvider,
		PlayerToolsProvider,
		RecorderToolsProvider