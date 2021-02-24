package com.lockwood.automata.res

inline class Px(val value: Number)
inline class Dp(val value: Number)
inline class Sp(val value: Number)

fun Number.toPx() = Px(this)
fun Number.toDp() = Dp(this)
fun Number.toSp() = Sp(this)