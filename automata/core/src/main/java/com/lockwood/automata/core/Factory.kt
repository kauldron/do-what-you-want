package com.lockwood.automata.core

interface Factory<E, T> {

    fun create(argument: E): T

}