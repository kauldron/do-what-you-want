package com.lockwood.replicant.executor

import java.util.concurrent.ExecutorService

interface ExecutorFactory {

	fun io(): ExecutorService

	fun network(): ExecutorService

	fun cpu(): ExecutorService

}
