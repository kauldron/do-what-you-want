package com.lockwood.automata.android

import android.content.Context
import android.util.DisplayMetrics
import com.lockwood.automata.res.Dp
import com.lockwood.automata.res.Px
import com.lockwood.automata.res.toDp
import com.lockwood.automata.res.toPx

private val Context.densityDpi: Float
    get() = resources.displayMetrics.densityDpi.toFloat()

private val Context.densityScale: Float
    get() = densityDpi / DisplayMetrics.DENSITY_DEFAULT

fun Context.dpToPx(dp: Dp): Px = (dp.value.toFloat() * densityScale).toPx()

fun Context.pxToDp(px: Px): Dp = (px.value.toFloat() / densityScale).toDp()