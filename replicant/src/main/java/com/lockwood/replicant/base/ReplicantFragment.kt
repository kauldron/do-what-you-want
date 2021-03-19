package com.lockwood.replicant.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.CallSuper
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
import com.lockwood.replicant.view.MessageView
import com.lockwood.replicant.view.ext.requireScreenView

abstract class ReplicantFragment<VS : ViewState> : Fragment(), MessageView {

	private companion object {

		private val TAG = ReplicantFragment::class.simpleName
	}

	@CallSuper
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		onBeforeObserveState()
		onObserveState()
	}

	override fun showMessage(message: String) = Unit

	override fun showError(message: String) = Unit

	protected open fun onBeforeObserveState() = Unit

	protected abstract fun onObserveState()

	protected abstract fun renderState(viewState: VS)

	@CallSuper
	protected open fun onOnEvent(event: Event) {
		Log.d(TAG, "onOnEvent: $event")
		when (event) {
			is MessageEvent -> showMessage(event.message)
			is ErrorMessageEvent -> showError(event.message)
			is GoToBackEvent -> requireScreenView().goBack()
			is ShowScreenEvent -> requireScreenView().showScreen(event.screen)
		}
	}

	protected inline fun <reified T : Feature> getFeature(): T {
		return application.getFeature()
	}

	protected inline fun <reified T : ReleasableFeature> releaseFeature() {
		application.releaseFeature<T>()
	}

}