package com.lockwood.automata.core.ext

import com.lockwood.automata.core.NotEmptyString
import kotlin.random.Random

fun NotEmptyString.isNotEmpty(): Boolean = true

fun NotEmptyString.isEmpty(): Boolean = false

fun NotEmptyString.isNullOrEmpty(): Boolean = false

fun NotEmptyString.isNullOrBlank(): Boolean = isBlank()

fun NotEmptyString.firstOrNull(): Char = first()

fun NotEmptyString.lastOrNull(): Char = last()

fun NotEmptyString.singleOrNull(): Char = single()

fun NotEmptyString.minOrNull(): Char = (this as CharSequence).minOrNull()!!

fun NotEmptyString.maxOrNull(): Char = (this as CharSequence).maxOrNull()!!

fun NotEmptyString.randomOrNull(): Char = (this as CharSequence).randomOrNull()!!

fun NotEmptyString.randomOrNull(random: Random): Char =
    (this as CharSequence).randomOrNull(random)!!