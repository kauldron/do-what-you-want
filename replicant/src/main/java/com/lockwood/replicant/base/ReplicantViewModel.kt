package com.lockwood.replicant.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lockwood.automata.core.notSafeLazy
import com.lockwood.replicant.event.Event
import com.lockwood.replicant.event.EventsQueue
import com.lockwood.replicant.event.ShowScreenEvent
import com.lockwood.replicant.ext.delegate
import com.lockwood.replicant.screen.Screen
import com.lockwood.replicant.state.ViewState
import com.lockwood.replicant.transform.StateTransformer
import kotlin.DeprecationLevel.WARNING

abstract class ReplicantViewModel<VS : ViewState>(initialState: VS) : ViewModel() {

	val liveState: MutableLiveData<VS> by notSafeLazy { MutableLiveData<VS>(initialState) }

	val eventsQueue by notSafeLazy { EventsQueue() }

	protected abstract val stateTransformer: StateTransformer<VS>

	protected var state: VS by liveState.delegate()

	@Deprecated(
			message = "Will be removed",
			replaceWith = ReplaceWith("withEvent"),
			level = WARNING
	)
	protected inline fun offerEvent(init: () -> Event) {
		val event = init()
		eventsQueue.offer(event)
	}

	@Deprecated(
			message = "Will be removed",
			replaceWith = ReplaceWith("stateReducer"),
			level = WARNING
	)
	protected inline fun mutateState(mutate: () -> VS) {
		state = mutate()
	}

	protected inline fun Any.withEvent(init: () -> Event) {
		val event = init()
		eventsQueue.offer(event)
	}

	protected fun navigateTo(screen: Screen) {
		offerEvent { ShowScreenEvent(screen) }
	}
}
