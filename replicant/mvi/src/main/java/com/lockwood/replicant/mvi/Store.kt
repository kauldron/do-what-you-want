package com.lockwood.replicant.mvi

import com.lockwood.replicant.mvi.`typealias`.EffectChangeListener
import com.lockwood.replicant.mvi.`typealias`.StateChangeListener

interface Store<State, Action> {

    val currentState: State

    fun accept(action: Action)

    fun listenState(listener: StateChangeListener<State>)
}

interface ImpureStore<State, Action, Effect> : Store<State, Action> {

    fun listenEffect(listener: EffectChangeListener<Effect>)
}