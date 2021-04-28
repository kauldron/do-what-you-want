package com.lockwood.replicant.view.ext

import android.view.View
import com.lockwood.replicant.view.listener.DebouncingOnClickListener

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
