package com.lockwood.replicant.executor

import kotlin.math.max

internal object Runtime {

    val CPU_COUNT = java.lang.Runtime.getRuntime().availableProcessors()
    val IO_MAX_POOL_SIZE = max(2, CPU_COUNT / 4)
    val NETWORK_MAX_POOL_SIZE = max(5, (CPU_COUNT / 1.5).toInt())

    const val CORE_POOL_SIZE = 0
    const val KEEP_ALIVE_TIME = 1L
}