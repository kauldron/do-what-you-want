package com.lockwood.dwyw.core

import com.lockwood.replicant.base.ReplicantViewModel
import com.lockwood.replicant.executor.provider.ExecutorProvider
import com.lockwood.replicant.state.ViewState

abstract class BaseViewModel<VS : ViewState>(
		initState: VS,
		@JvmField
		private val executorProvider: ExecutorProvider
) : ReplicantViewModel<VS>(initState) {

	private companion object {

		private const val DEFAULT_LOADING_DELAY = 500L
	}

	protected fun postDelay(action: Runnable) {
		executorProvider.postMainDelay(action, DEFAULT_LOADING_DELAY)
	}

}
