package com.lockwood.replicant.executor

import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

interface ExecutorFactory {

    fun main(): Executor

    fun io(): ExecutorService

    fun network(): ExecutorService

    fun cpu(): ExecutorService

}
