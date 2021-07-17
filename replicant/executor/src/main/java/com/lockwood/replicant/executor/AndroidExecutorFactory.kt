package com.lockwood.replicant.executor

import android.content.Context
import android.os.Build
import android.os.Handler
import com.lockwood.replicant.executor.Runtime.CORE_POOL_SIZE
import com.lockwood.replicant.executor.Runtime.CPU_COUNT
import com.lockwood.replicant.executor.Runtime.IO_MAX_POOL_SIZE
import com.lockwood.replicant.executor.Runtime.KEEP_ALIVE_TIME
import com.lockwood.replicant.executor.Runtime.NETWORK_MAX_POOL_SIZE
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit.MINUTES

class AndroidExecutorFactory(
    private val context: Context,
) : ExecutorFactory {

    override fun main(): Executor = with(context) {
        return if (Build.VERSION.SDK_INT >= 28) {
            mainExecutor
        } else {
            HandlerExecutor(Handler(mainLooper))
        }
    }

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