package com.lockwood.replicant.view.ext

import android.view.View
import com.lockwood.replicant.view.listener.DebouncingOnClickListener
import com.lockwood.replicant.view.listener.DebouncingOnClickListener.Companion.DOUBLE_TAP_TIMEOUT

inline fun View.setDebouncingOnClickListener(
		timeout: Long = DOUBLE_TAP_TIMEOUT,
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
