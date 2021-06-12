package com.lockwood.dwyw

import com.lockwood.dwyw.core.feature.CoreToolsProvider
import com.lockwood.dwyw.core.feature.wrapper.BuildConfigWrapperToolsProvider
import com.lockwood.replicant.context.ApplicationContextProvider

//import com.lockwood.room.feature.RoomToolsProvider

interface DoWhatYouWantApplication :
    ApplicationContextProvider,
    CoreToolsProvider,
//		ConnectionsToolsProvider,
//		RoomToolsProvider,
    BuildConfigWrapperToolsProvider
//		PlayerToolsProvider,
//		RecorderToolsProvider