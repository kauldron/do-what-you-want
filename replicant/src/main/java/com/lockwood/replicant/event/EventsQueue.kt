package com.lockwood.replicant.event

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import java.util.Queue
import java.util.LinkedList

interface Event

class EventsQueue : MutableLiveData<Queue<Event>>() {

    @MainThread
    fun offer(event: Event) {
        val queue = (value ?: LinkedList()).apply {
            add(event)
        }
        value = queue
    }

}

fun Fragment.observe(eventsQueue: EventsQueue, eventHandler: (Event) -> Unit) {
    eventsQueue.observe(viewLifecycleOwner, { queue: Queue<Event>? ->
        while (!queue.isNullOrEmpty()) {
            eventHandler(queue.remove())
        }
    })
}