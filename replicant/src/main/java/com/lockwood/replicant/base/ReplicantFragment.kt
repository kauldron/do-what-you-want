package com.lockwood.replicant.base

import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import com.lockwood.automata.android.application
import com.lockwood.automata.core.calculateHashCode
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

abstract class ReplicantFragment<VS : ViewState> : FeatureFragment() {

	private val rememberStore: FunctionStore = FunctionStore()

	override fun onDestroyView() {
		rememberStore.removeAll()
		super.onDestroyView()
	}

	protected abstract fun renderState(viewState: VS)

	protected fun <P1, R> remember(
			action: ((P1) -> R),
			first: P1
	) = rememberStore.remember(first, action)

	protected fun <P1, P2, R> remember(
			action: ((P1, P2) -> R),
			first: P1,
			second: P2
	) = rememberStore.remember(first, second, action)

	protected fun <P1, P2, P3, R> remember(
			action: ((P1, P2, P3) -> R),
			first: P1,
			second: P2,
			third: P3
	) = rememberStore.remember(first, second, third, action)

}

private class FunctionStore {

	private val store: MutableMap<Int, Int> = mutableMapOf()

	fun <P1, R> remember(first: P1, action: ((P1) -> R)) {
		val hashPair = action.hashCode() to calculateHashCode(first)

		rememberInvoke(hashPair) { action.invoke(first) }
	}

	fun <P1, P2, R> remember(first: P1, second: P2, action: ((P1, P2) -> R)) {
		val hashPair = action.hashCode() to calculateHashCode(first, second)

		rememberInvoke(hashPair) { action.invoke(first, second) }
	}

	fun <P1, P2, P3, R> remember(first: P1, second: P2, third: P3, action: ((P1, P2, P3) -> R)) {
		val hashPair = action.hashCode() to calculateHashCode(first, second, third)

		rememberInvoke(hashPair) { action.invoke(first, second, third) }
	}

	fun removeAll() {
		store.clear()
	}

	private inline fun rememberInvoke(hashPair: Pair<Int, Int>, action: () -> Unit) {
		if (hasInStore(hashPair)) {
			// do nothing if already call this function with this args
			return
		} else {
			// remember that we call function with this args and call it
			putToStore(hashPair)
			action()
		}
	}

	private fun putToStore(hash: Pair<Int, Int>) {
		store[hash.first] = hash.second
	}

	private fun hasInStore(hash: Pair<Int, Int>): Boolean {
		return store.containsKey(hash.first) && store[hash.first] == hash.second
	}

}
