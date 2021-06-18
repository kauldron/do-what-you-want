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
fun Context.dpToPx(@Dimension dp: Int): Int = (dp * densityScale).toInt()

@Dimension
fun Context.pxToDp(@Px px: Int): Int = (px / densityScale).toInt()
