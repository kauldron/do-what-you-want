package com.lockwood.room.feature.discover.model

import com.lockwood.room.model.Room

@JvmInline
internal value class RoomsArray(
    @JvmField
    val value: Array<Room>,
)