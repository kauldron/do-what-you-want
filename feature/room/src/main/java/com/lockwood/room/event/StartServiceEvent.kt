package com.lockwood.room.event

import com.lockwood.replicant.event.StartServiceEvent

object StartHostServiceEvent : StartServiceEvent()

object StopHostServiceEvent : StartServiceEvent()

object StartClientServiceEvent : StartServiceEvent()

object StopClientServiceEvent : StartServiceEvent()
