package com.lockwood.replicant.inflater

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.UiThread

class AsyncLayoutInflater(
		context: Context
) : BaseAsyncLayoutInflater(context) {

	private companion object {

		private const val TAG = "AsyncLayoutInflater"
	}

	override fun cloneInContext(
			newContext: Context
	): LayoutInflater = AsyncLayoutInflater(newContext)

	@UiThread
	override fun inflate(
			@LayoutRes resId: Int,
			viewGroup: ViewGroup?,
			callback: (View, Int, ViewGroup?) -> Unit,
	) {
		try {
			inflateOnWorkerThread(resId, viewGroup, callback)
		} catch (e: RuntimeException) {
			Log.w(TAG, "Failed to inflate in the background ${e.javaClass.simpleName}! Retrying on the UI thread")
			inflateOnUiThread(resId, viewGroup, callback)
		}
	}

	@kotlin.jvm.Throws(RuntimeException::class)
	private fun inflateOnWorkerThread(
			@LayoutRes resId: Int,
			viewGroup: ViewGroup?,
			callback: (View, Int, ViewGroup?) -> Unit
	) = runOnWorkerThread {
		val view = inflate(resId, viewGroup, false)
		runOnUiThread { callback.invoke(view, resId, viewGroup) }
	}

	private fun inflateOnUiThread(
			@LayoutRes resId: Int,
			viewGroup: ViewGroup?,
			callback: (View, Int, ViewGroup?) -> Unit
	) = runOnUiThread {
		val view = inflate(resId, viewGroup, false)
		callback.invoke(view, resId, viewGroup)
	}

}