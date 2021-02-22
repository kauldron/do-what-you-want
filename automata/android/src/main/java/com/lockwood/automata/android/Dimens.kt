package com.lockwood.automata.android

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.annotation.Px
import com.lockwood.automata.core.ZERO

private val Context.densityDpi: Float
    get() = resources.displayMetrics.densityDpi.toFloat()

private val Context.densityScale: Float
    get() = densityDpi / DisplayMetrics.DENSITY_DEFAULT

val Resources.navBarHeight: Int
    @Px
    get() {
        val resourceId: Int = getIdentifier(
            "navigation_bar_height",
            "dimen",
            "android"
        )

        return resourceId.takeIf { it.isValidResource } ?: Int.ZERO
    }

fun Context.dpToPx(dp: Number): Number = dp.toFloat() * densityScale

fun Context.pxToDp(px: Number): Number = px.toFloat() / densityScale