package com.lockwood.automata.core

fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }