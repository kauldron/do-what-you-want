package com.lockwood.replicant.mvi.base

import com.lockwood.replicant.mvi.Middleware
import com.lockwood.replicant.mvi.Store

abstract class BaseMiddleWare<State, Action> : Middleware<State, Action> {

    protected lateinit var store: Store<State, Action>

    override fun bind(storeToBind: Store<State, Action>) {
        store = storeToBind
    }

}