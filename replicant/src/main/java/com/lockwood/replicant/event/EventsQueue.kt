package com.lockwood.replicant.event

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import java.util.LinkedList
import java.util.Queue

interface Event

class EventsQueue : MutableLiveData<Queue<Event>>() {

	@MainThread
	fun offer(event: Event) {
		val queue = (value ?: LinkedList()).apply { add(event) }
		value = queue
	}
}

fun Fragment.observeEvents(
		eventsQueue: EventsQueue,
		eventHandler: (Event) -> Unit,
) = viewLifecycleOwner.observeEvents(eventsQueue, eventHandler)

fun FragmentActivity.observeEvents(
		eventsQueue: EventsQueue,
		eventHandler: (Event) -> Unit,
) = (this as LifecycleOwner).observeEvents(eventsQueue, eventHandler)

private inline fun LifecycleOwner.observeEvents(
		eventsQueue: EventsQueue,
		crossinline eventHandler: (Event) -> Unit,
) {
	eventsQueue.observe(
			this,
			{ queue: Queue<Event>? ->
				while (!queue.isNullOrEmpty()) {
					eventHandler(queue.remove())
				}
			}
	)
}
