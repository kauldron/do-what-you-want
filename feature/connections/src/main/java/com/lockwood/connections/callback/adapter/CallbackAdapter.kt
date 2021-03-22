package com.lockwood.connections.callback.adapter

internal interface CallbackAdapter<T> {

  fun addListener(callback: T)
}
