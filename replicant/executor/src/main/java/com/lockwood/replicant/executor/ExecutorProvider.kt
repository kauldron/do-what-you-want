package com.lockwood.replicant.executor

import java.util.concurrent.Executor

interface ExecutorProvider {

    fun main(): Executor

    fun io(): Executor

    fun network(): Executor

    fun cpu(): Executor

}