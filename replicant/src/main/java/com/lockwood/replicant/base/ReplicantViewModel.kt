package com.lockwood.replicant.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lockwood.replicant.event.ErrorMessageEvent
import com.lockwood.replicant.event.EventsQueue
import com.lockwood.replicant.event.MessageEvent
import com.lockwood.replicant.event.ShowScreenEvent
import com.lockwood.replicant.ext.delegate
import com.lockwood.replicant.screen.Screen
import com.lockwood.replicant.state.ViewState

abstract class ReplicantViewModel<VS : ViewState>(initialState: VS) : ViewModel() {

    val liveState: MutableLiveData<VS> by lazy { MutableLiveData<VS>(initialState) }

    val eventsQueue by lazy { EventsQueue() }

    protected var state: VS by liveState.delegate()

    protected inline fun offerEvents(offer: EventsQueue.() -> Unit) {
        eventsQueue.offer()
    }

    protected inline fun mutateState(mutate: () -> VS) {
        state = mutate()
    }

    protected fun showMessage(message: String) = offerEvents {
        val event = MessageEvent(message)
        offer(event)
    }

    protected fun showErrorMessage(message: String) = offerEvents {
        val event = ErrorMessageEvent(message)
        offer(event)
    }

    protected fun navigateTo(screen: Screen) = offerEvents {
        val event = ShowScreenEvent(screen)
        offer(event)
    }

}