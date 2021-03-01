package com.lockwood.automata.screen

import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import com.lockwood.automata.res.Px
import com.lockwood.automata.res.toPx

@Suppress("DEPRECATION")
val Activity.displayMetrics: DisplayMetrics
    @kotlin.jvm.Throws(IllegalArgumentException::class)
    get() {

        val displayMetrics = DisplayMetrics()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            windowManager.defaultDisplay.getMetrics(displayMetrics)
        } else {
            requireNotNull(display).getRealMetrics(displayMetrics)
        }

        return displayMetrics
    }

val Activity.screenWidth: Px
    get() = displayMetrics.widthPixels.toPx()

val Activity.screenHeight: Px
    get() = displayMetrics.heightPixels.toPx()