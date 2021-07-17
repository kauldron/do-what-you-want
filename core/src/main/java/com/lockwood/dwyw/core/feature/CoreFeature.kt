package com.lockwood.dwyw.core.feature

import com.lockwood.replicant.context.ApplicationContextProvider
import com.lockwood.replicant.executor.AndroidExecutorFactory
import com.lockwood.replicant.executor.ExecutorFactory
import com.lockwood.replicant.feature.Feature
import com.lockwood.replicant.releasable.notSafeReleasableLazy

class CoreFeature(
    private val contextProvider: ApplicationContextProvider,
) : Feature {

    val executorFactory: ExecutorFactory by notSafeReleasableLazy {
        AndroidExecutorFactory(contextProvider.applicationContext.value)
    }

}
