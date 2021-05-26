package com.lockwood.replicant.base

import com.lockwood.replicant.feature.base.FeatureActivity
import com.lockwood.replicant.screen.Screen
import com.lockwood.replicant.view.ScreenView

abstract class ScreenActivity : FeatureActivity(), ScreenView {

	private companion object {

		private val TAG = FeatureActivity::class.simpleName
	}

	override fun goBack() {
		onBackPressed()
	}

	override fun showScreen(screen: Screen) {
		error("Unknown screen: $screen")
	}

//	@CallSuper
//	protected open fun onEvent(event: Event) {
//		Log.d(TAG, "onEvent: $event")
//		when (event) {
//			is MessageEvent -> requireMessageView().showMessage(event.message)
//			is ErrorMessageEvent -> requireMessageView().showError(event.message)
//			is GoToBackEvent -> requireScreenView().goBack()
//			is ShowScreenEvent -> requireScreenView().showScreen(event.screen)
//			else -> error("Unknown event: $event")
//		}
//	}

}

abstract class ReplicantActivity<State> : ScreenActivity() {

	protected abstract fun renderState(viewState: State)

}
