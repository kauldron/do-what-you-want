package com.lockwood.replicant.view.listener

import android.os.Handler
import android.os.Looper
import android.view.View

abstract class DebouncingOnClickListener(
    private val timeout: Long = DOUBLE_TAP_TIMEOUT,
) : View.OnClickListener {

    companion object {
        const val DOUBLE_TAP_TIMEOUT = 300L

        private val MAIN: Handler = Handler(Looper.getMainLooper())

        var enabled = true
    }

    override fun onClick(v: View) {
        if (enabled) {
            enabled = false
            enableClickAgain()
            doClick(v)
        }
    }

    abstract fun doClick(v: View)

    private fun enableClickAgain() {
        MAIN.postDelayed({ enabled = true }, timeout)
    }
}

inline fun View.setDebouncingOnClickListener(
    crossinline onClick: () -> Unit,
) {
    setOnClickListener(
        object : DebouncingOnClickListener(DOUBLE_TAP_TIMEOUT) {
            override fun doClick(v: View) {
                onClick()
            }
        }
    )
}

inline fun View.setDebouncingOnClickListener(
    timeout: Long,
    crossinline onClick: () -> Unit,
) {
    setOnClickListener(
        object : DebouncingOnClickListener(timeout) {
            override fun doClick(v: View) {
                onClick()
            }
        }
    )
}

