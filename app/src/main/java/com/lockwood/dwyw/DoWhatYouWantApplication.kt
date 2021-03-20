package com.lockwood.dwyw

import com.lockwood.direct.feature.DirectToolsProvider
import com.lockwood.replicant.context.ApplicationContextProvider
import com.lockwood.room.feature.RoomToolsProvider

interface DoWhatYouWantApplication :
  ApplicationContextProvider, DirectToolsProvider, RoomToolsProvider
