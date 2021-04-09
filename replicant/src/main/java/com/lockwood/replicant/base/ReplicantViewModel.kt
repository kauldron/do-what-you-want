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

abstract class ReplicantViewModel<VS : ViewState>(initialState: VS) : ViewModel() {

	val liveState: MutableLiveData<VS> by notSafeLazy { MutableLiveData<VS>(initialState) }

	val eventsQueue by notSafeLazy { EventsQueue() }

	protected var state: VS by liveState.delegate()

	protected inline fun offerEvent(init: () -> Event) {
		val event = init()
		eventsQueue.offer(event)
	}

	protected inline fun mutateState(mutate: () -> VS) {
		state = mutate()
	}

	protected fun navigateTo(screen: Screen) {
		offerEvent { ShowScreenEvent(screen) }
	}
}
