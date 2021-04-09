package com.lockwood.replicant.imageloader.target

import android.view.View

interface ViewTarget<T : View> : Target {

	/** The View used by this Target. */
	val targetView: T
}
