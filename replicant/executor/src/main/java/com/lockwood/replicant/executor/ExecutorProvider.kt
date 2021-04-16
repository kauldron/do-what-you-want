package com.lockwood.replicant.executor

import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

interface ExecutorProvider {

	fun main(): Executor

	fun io(): ExecutorService

	fun network(): ExecutorService

	fun cpu(): ExecutorService

	fun postMain(runnable: Runnable)

	fun postMainDelay(runnable: Runnable, delay: Number)
}
