package com.lockwood.automata.android

import android.content.Context
import android.util.DisplayMetrics
import androidx.annotation.Dimension
import androidx.annotation.Px

private val Context.densityDpi: Float
	get() = resources.displayMetrics.densityDpi.toFloat()

private val Context.densityScale: Float
	get() = densityDpi / DisplayMetrics.DENSITY_DEFAULT

@Px
fun Context.dpToPx(@Dimension dp: Int): Float = dp * densityScale

@Dimension
fun Context.pxToDp(@Px px: Int): Float = px / densityScale
