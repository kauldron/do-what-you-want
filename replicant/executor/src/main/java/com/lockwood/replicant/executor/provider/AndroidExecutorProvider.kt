package com.lockwood.replicant.executor.provider

import android.os.Handler
import android.os.Looper
import com.lockwood.replicant.executor.ExecutorFactory
import com.lockwood.replicant.executor.ExecutorServiceFactory
import java.util.concurrent.Executor

class AndroidExecutorProvider(
		private val executorFactory: ExecutorFactory = ExecutorServiceFactory,
		private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())
) : ExecutorProvider, ExecutorFactory by executorFactory {

	private companion object {

		@JvmStatic
		private val isMainThread: Boolean
			get() = Thread.currentThread() !== Looper.getMainLooper().thread
	}

	override fun main(): Executor = Executor { runOnUiThread(it) }

	override fun postMain(runnable: Runnable) {
		mainThreadHandler.post(runnable)
	}

	override fun postMainDelay(runnable: Runnable, delay: Number) {
		mainThreadHandler.postDelayed(runnable, delay.toLong())
	}

	private fun runOnUiThread(action: Runnable) {
		if (!isMainThread) {
			mainThreadHandler.post(action)
		} else {
			action.run()
		}
	}
}
