package com.lockwood.room.feature.discover.event

import com.lockwood.replicant.event.Event
import com.lockwood.room.model.Room

internal class ShowAcceptConnectionEvent(val room: Room) : Event