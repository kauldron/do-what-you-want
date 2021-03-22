package com.lockwood.dwyw

import com.lockwood.connections.feature.ConnectionsToolsProvider
import com.lockwood.replicant.context.ApplicationContextProvider
import com.lockwood.room.feature.RoomToolsProvider

interface DoWhatYouWantApplication :
  ApplicationContextProvider, ConnectionsToolsProvider, RoomToolsProvider
