package com.lockwood.replicant.view.ext

import android.view.View
import com.lockwood.replicant.view.listener.DebouncingOnClickListener
import com.lockwood.replicant.view.listener.DebouncingOnClickListener.Companion.DOUBLE_TAP_TIMEOUT

fun View.setDebouncingOnClickListener(
    onClick: View.() -> Unit,
    timeout: Long = DOUBLE_TAP_TIMEOUT,
) {
    setOnClickListener(object : DebouncingOnClickListener(timeout) {
        override fun doClick(v: View) {
            v.onClick()
        }
    })
}