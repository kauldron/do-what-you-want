package com.lockwood.replicant.mvi

typealias Reducer<State, Action, Effect> = (action: Action, state: State) -> Pair<State, List<Effect>>
typealias PureReducer<State, Action> = Reducer<State, Action, Nothing>

fun listOfNothing(): List<Nothing> = emptyList()
fun <State> pairOfNothing(value: State): Pair<State, List<Nothing>> = value to listOfNothing()


