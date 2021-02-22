package com.lockwood.automata.android

import android.content.Context
import androidx.core.content.ContextCompat

@kotlin.jvm.Throws(IllegalArgumentException::class)
inline fun <reified T> Context.getSystemService(): T {
    val service = ContextCompat.getSystemService(this, T::class.java)
    return requireNotNull(service)
}