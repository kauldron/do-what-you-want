package com.lockwood.replicant.executor

import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit.MINUTES
import kotlin.math.max

object ExecutorServiceFactory : ExecutorFactory {

	private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
	private val IO_MAX_POOL_SIZE = max(2, CPU_COUNT / 4)
	private val NETWORK_MAX_POOL_SIZE = max(5, (CPU_COUNT / 1.5).toInt())

	private const val CORE_POOL_SIZE = 0
	private const val KEEP_ALIVE_TIME = 1L

	override fun io(): ExecutorService = buildThreadPoolExecutor(IO_MAX_POOL_SIZE)

	override fun network(): ExecutorService = buildThreadPoolExecutor(NETWORK_MAX_POOL_SIZE)

	override fun cpu(): ExecutorService = buildThreadPoolExecutor(CPU_COUNT)

	private fun buildThreadPoolExecutor(maximumPoolSize: Int): ExecutorService {
		return ThreadPoolExecutor(
				CORE_POOL_SIZE,
				maximumPoolSize,
				KEEP_ALIVE_TIME,
				MINUTES,
				LinkedBlockingQueue()
		)
	}

}