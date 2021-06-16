package com.lockwood.replicant.mvi.base

import com.lockwood.replicant.mvi.ImpureStore
import com.lockwood.replicant.mvi.Middleware
import com.lockwood.replicant.mvi.Reducer
import com.lockwood.replicant.mvi.Releasable
import com.lockwood.replicant.mvi.`typealias`.EffectChangeListener
import com.lockwood.replicant.mvi.`typealias`.StateChangeListener

abstract class BaseStore<State, Action, Effect>(
    initialState: State,
    private val reducer: Reducer<State, Action, Effect>,
    private val middlewares: List<Middleware<State, Action>> = emptyList(),
) : ImpureStore<State, Action, Effect>, Releasable {

    init {
        middlewares.forEach { it.bind(this) }
    }

    private val stateListeners = mutableListOf<StateChangeListener<State>>()
    private val effectListeners = mutableListOf<EffectChangeListener<Effect>>()

    override var currentState: State = initialState

    override fun accept(action: Action) {
        middlewares.forEach { it.accept(action) }

        val (state, effects) = reducer.invoke(action, currentState)

        currentState = state
        notifyAllStateChanged(state)
        notifyAllEffectsHappened(effects)
    }

    override fun listenState(listener: StateChangeListener<State>) {
        stateListeners.add(listener)
    }

    override fun listenEffect(listener: EffectChangeListener<Effect>) {
        effectListeners.add(listener)
    }

    private fun notifyAllStateChanged(state: State) {
        stateListeners.forEach { listener -> listener.invoke(state) }
    }

    private fun notifyAllEffectsHappened(effects: List<Effect>) {
        effects.forEach { effect -> effectListeners.forEach { listener -> listener.invoke(effect) } }
    }

    override fun release() {
        stateListeners.clear()
        effectListeners.clear()
    }
}