package com.lockwood.replicant.inflater

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import java.util.concurrent.ExecutorService
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

abstract class BaseAsyncLayoutInflater(
		context: Context,
		private val executorService: ExecutorService
) : LayoutInflater(context) {

	private val lock: Lock = ReentrantLock()

	private val mainHandler: Handler = Handler(Looper.getMainLooper())

	@UiThread
	abstract fun inflate(
			@LayoutRes resId: Int,
			viewGroup: ViewGroup? = null,
			callback: (View, Int, ViewGroup?) -> Unit
	)

	@WorkerThread
	protected abstract fun tryInflateAsync(
			@LayoutRes resId: Int,
			viewGroup: ViewGroup?,
			callback: (View, Int, ViewGroup?) -> Unit
	)

	protected fun runOnUiThread(action: Runnable) {
		mainHandler.post(action)
	}

	protected fun runOnWorkerThread(action: Runnable) = lock.withLock {
		executorService.execute(action)
	}

}