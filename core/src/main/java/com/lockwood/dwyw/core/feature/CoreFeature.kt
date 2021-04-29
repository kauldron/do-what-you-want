package com.lockwood.dwyw.core.feature

import com.lockwood.replicant.executor.provider.AndroidExecutorProvider
import com.lockwood.replicant.executor.provider.ExecutorProvider
import com.lockwood.replicant.ext.notSafeReleasableLazy
import com.lockwood.replicant.feature.Feature

class CoreFeature : Feature {

	val executorProvider: ExecutorProvider by notSafeReleasableLazy {
		AndroidExecutorProvider()
	}

}
