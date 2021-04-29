package com.lockwood.replicant.executor.provider

import com.lockwood.replicant.executor.ExecutorFactory
import java.util.concurrent.Executor

interface ExecutorProvider : ExecutorFactory {

	fun main(): Executor

	fun postMain(runnable: Runnable)

	fun postMainDelay(runnable: Runnable, delay: Number)
}
