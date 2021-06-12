package com.lockwood.replicant.mvi.function

import com.lockwood.automata.core.calculateHashCode
import com.lockwood.replicant.mvi.Releasable

class FunctionStore : Releasable {

    private val store: MutableMap<Int, Int> = mutableMapOf()

    override fun release() {
        store.clear()
    }

    fun <P1, T> remember(first: P1, action: ((P1) -> T)) {
        val hashPair = action.hashCode() to calculateHashCode(first)

        rememberInvoke(hashPair) { action.invoke(first) }
    }

    fun <P1, P2, T> remember(first: P1, second: P2, action: ((P1, P2) -> T)) {
        val hashPair = action.hashCode() to calculateHashCode(first, second)

        rememberInvoke(hashPair) { action.invoke(first, second) }
    }

    fun <P1, P2, P3, T> remember(first: P1, second: P2, third: P3, action: ((P1, P2, P3) -> T)) {
        val hashPair = action.hashCode() to calculateHashCode(first, second, third)

        rememberInvoke(hashPair) { action.invoke(first, second, third) }
    }

    private inline fun rememberInvoke(hashPair: Pair<Int, Int>, action: () -> Unit) {
        if (hasInStore(hashPair)) {
            // do nothing if already call this function with this args
            return
        } else {
            // remember that we call function with this args and call it
            putToStore(hashPair)
            action()
        }
    }

    private fun putToStore(hash: Pair<Int, Int>) {
        store[hash.first] = hash.second
    }

    private fun hasInStore(hash: Pair<Int, Int>): Boolean {
        return store.containsKey(hash.first) && store[hash.first] == hash.second
    }

}