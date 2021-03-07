package com.lockwood.room.data

data class Room(
    val id: Int,
    val name: String,
    val isActive: Boolean = false // ENUMs?
)