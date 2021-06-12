package com.lockwood.replicant.inflater.ext

import android.app.Activity
import android.view.View
import com.lockwood.replicant.inflater.AsyncLayoutInflater

inline fun Activity.setContentViewAsync(
    resID: Int,
    crossinline callback: (View) -> Unit,
) = AsyncLayoutInflater(this).inflate(resID) { view, _, _ ->
    setContentView(view)
    callback.invoke(view)
}