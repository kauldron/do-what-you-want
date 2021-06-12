package com.lockwood.replicant.factory

interface Factory<E, T> {

    fun create(argument: E): T
}
