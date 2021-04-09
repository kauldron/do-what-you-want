package com.lockwood.replicant.base

import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.fragment.app.FragmentActivity
import com.lockwood.replicant.event.ErrorMessageEvent
import com.lockwood.replicant.event.Event
import com.lockwood.replicant.event.GoToBackEvent
import com.lockwood.replicant.event.MessageEvent
import com.lockwood.replicant.event.ShowScreenEvent
import com.lockwood.replicant.ext.getFeature
import com.lockwood.replicant.ext.releaseFeature
import com.lockwood.replicant.feature.Feature
import com.lockwood.replicant.feature.ReleasableFeature
import com.lockwood.replicant.screen.Screen
import com.lockwood.replicant.state.ViewState
import com.lockwood.replicant.view.ScreenView
import com.lockwood.replicant.view.ext.requireMessageView
import com.lockwood.replicant.view.ext.requireScreenView

abstract class FeatureActivity : FragmentActivity(), ScreenView {

	private companion object {

		private val TAG = FeatureActivity::class.simpleName
	}

	override fun goBack() {
		onBackPressed()
	}

	override fun showScreen(screen: Screen) {
		error("Unknown screen: $screen")
	}

	@CallSuper
	protected open fun onEvent(event: Event) {
		Log.d(TAG, "onEvent: $event")
		when (event) {
			is MessageEvent -> requireMessageView().showMessage(event.message)
			is ErrorMessageEvent -> requireMessageView().showError(event.message)
			is GoToBackEvent -> requireScreenView().goBack()
			is ShowScreenEvent -> requireScreenView().showScreen(event.screen)
			else -> error("Unknown event: $event")
		}
	}

	@MainThread
	protected inline fun <reified T : Feature> getFeature(): T {
		return application.getFeature()
	}

	@MainThread
	protected inline fun <reified T : ReleasableFeature> releaseFeature() {
		application.releaseFeature<T>()
	}
}

abstract class ReplicantActivity<VS : ViewState> : FeatureActivity() {

	protected abstract fun renderState(viewState: VS)
}
