package com.lockwood.dwyw.core

import com.lockwood.replicant.base.ReplicantViewModel
import com.lockwood.replicant.executor.ExecutorProvider
import com.lockwood.replicant.state.ViewState

abstract class BaseViewModel<VS : ViewState>(
		initState: VS,
		private val executorProvider: ExecutorProvider
) : ReplicantViewModel<VS>(initState) {

	private companion object {

		private const val DEFAULT_LOADING_DELAY = 500L
	}

	protected fun postDelay(action: Runnable, delay: Long) {
		executorProvider.postMainDelay(action, delay)
	}

	protected fun postDelay(action: Runnable) {
		executorProvider.postMainDelay(action, DEFAULT_LOADING_DELAY)
	}

	protected fun runOnUiThread(action: Runnable) {
		executorProvider.postMain(action)
	}
}
