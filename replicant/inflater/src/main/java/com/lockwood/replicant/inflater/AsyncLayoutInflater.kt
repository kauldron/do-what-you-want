package com.lockwood.replicant.inflater

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit.MINUTES

class AsyncLayoutInflater(
		context: Context,
		executorService: ExecutorService = ThreadPoolExecutor(
				CORE_POOL_SIZE,
				MAX_POOL_SIZE,
				KEEP_ALIVE_TIME,
				MINUTES,
				LinkedBlockingQueue()
		)
) : BaseAsyncLayoutInflater(context, executorService) {

	private companion object {

		private const val TAG = "AsyncLayoutInflater"


		private val CPU_COUNT: Int = Runtime.getRuntime().availableProcessors()
		private val MAX_POOL_SIZE: Int = 2.coerceAtLeast(CPU_COUNT / 4)

		private const val CORE_POOL_SIZE: Int = 0
		private const val KEEP_ALIVE_TIME: Long = 1L
	}

	private val basicInflater: LayoutInflater = BasicInflater(context)

	override fun cloneInContext(newContext: Context): LayoutInflater = BasicInflater(newContext)

	@UiThread
	override fun inflate(
			@LayoutRes resId: Int,
			viewGroup: ViewGroup?,
			callback: (View, Int, ViewGroup?) -> Unit,
	): Unit = runOnWorkerThread { tryInflateAsync(resId, viewGroup, callback) }

	@WorkerThread
	override fun tryInflateAsync(
			@LayoutRes resId: Int,
			viewGroup: ViewGroup?,
			callback: (View, Int, ViewGroup?) -> Unit
	) {
		try {
			val view = basicInflater.inflate(resId, viewGroup, false)
			runOnUiThread {
				callback.invoke(view, resId, viewGroup)
			}
		} catch (e: RuntimeException) {
			Log.w(
					TAG,
					"Failed to inflate resource in the background cause ${e.javaClass.simpleName}! Retrying on the UI thread"
			)

			runOnUiThread {
				val view = basicInflater.inflate(resId, viewGroup, false)
				callback.invoke(view, resId, viewGroup)
			}
		}
	}

}