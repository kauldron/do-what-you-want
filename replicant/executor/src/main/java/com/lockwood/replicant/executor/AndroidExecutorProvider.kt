package com.lockwood.replicant.executor

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.math.max

class AndroidExecutorProvider : ExecutorProvider {

	private companion object {

		@JvmStatic
		private val isMainThread: Boolean
			get() = Thread.currentThread() !== Looper.getMainLooper().thread

		private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
		private val IO_MAX_POOL_SIZE = max(2, CPU_COUNT / 4)
		private val NETWORK_MAX_POOL_SIZE = max(5, (CPU_COUNT / 1.5).toInt())

		private const val CORE_POOL_SIZE = 0
		private const val KEEP_ALIVE_TIME = 1L
	}

	private val ioExecutor: Executor
		get() = buildThreadPoolExecutor(IO_MAX_POOL_SIZE)

	private val networkExecutor: Executor
		get() = buildThreadPoolExecutor(NETWORK_MAX_POOL_SIZE)

	private val cpuExecutor: Executor
		get() = buildThreadPoolExecutor(CPU_COUNT)

	private val mainExecutor: Executor
		get() = Executor { runOnUiThread(it) }

	private val mainThreadHandler = Handler(Looper.getMainLooper())

	override fun main(): Executor = mainExecutor

	override fun io(): Executor = ioExecutor

	override fun network(): Executor = networkExecutor

	override fun cpu(): Executor = cpuExecutor

	override fun postMain(runnable: Runnable) {
		mainThreadHandler.post(runnable)
	}

	override fun postMainDelay(runnable: Runnable, delay: Number) {
		mainThreadHandler.postDelayed(runnable, delay.toLong())
	}

	private fun buildThreadPoolExecutor(maximumPoolSize: Int): Executor {
		return ThreadPoolExecutor(
				CORE_POOL_SIZE,
				maximumPoolSize,
				KEEP_ALIVE_TIME,
				TimeUnit.MINUTES,
				LinkedBlockingQueue()
		)
	}

	private fun runOnUiThread(action: Runnable) {
		if (!isMainThread) {
			mainThreadHandler.post(action)
		} else {
			action.run()
		}
	}
}
