package com.lockwood.replicant.imageloader.target

import android.graphics.drawable.Drawable

/** A listener that accepts the result of an image request. */
interface Target {

    /** Called when the request starts. */
    fun onStart(placeholder: Drawable) {}

    /** Called if the request completes successfully. */
    fun onSuccess(result: Drawable) {}

    /** Called if an error occurs while executing the request. */
    fun onError(error: Drawable) {}
}
