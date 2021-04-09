package com.lockwood.replicant.base

import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import com.lockwood.automata.android.application
import com.lockwood.replicant.event.ErrorMessageEvent
import com.lockwood.replicant.event.Event
import com.lockwood.replicant.event.GoToBackEvent
import com.lockwood.replicant.event.MessageEvent
import com.lockwood.replicant.event.ShowScreenEvent
import com.lockwood.replicant.ext.getFeature
import com.lockwood.replicant.ext.releaseFeature
import com.lockwood.replicant.feature.Feature
import com.lockwood.replicant.feature.ReleasableFeature
import com.lockwood.replicant.state.ViewState
import com.lockwood.replicant.view.ext.requireMessageView
import com.lockwood.replicant.view.ext.requireScreenView

abstract class FeatureFragment : Fragment() {

	private companion object {

		private val TAG = FeatureFragment::class.simpleName
	}

	@CallSuper
	protected open fun onEvent(event: Event) {
		Log.d(TAG, "onOnEvent: $event")
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

abstract class ReplicantFragment<VS : ViewState> : FeatureFragment() {

	protected abstract fun renderState(viewState: VS)
}
