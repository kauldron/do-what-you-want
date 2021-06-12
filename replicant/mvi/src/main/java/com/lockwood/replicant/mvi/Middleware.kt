package com.lockwood.replicant.mvi

interface Middleware<State, Action> : Releasable {

    fun bind(storeToBind: Store<State, Action>)

    fun accept(action: Action)

}

