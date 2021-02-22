package com.lockwood.replicant.res

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface ResourceReader {

    fun color(@ColorRes res: Int): Int

    fun string(@StringRes res: Int): String

    fun string(@StringRes res: Int, vararg args: String): String

    fun drawable(@DrawableRes res: Int): Drawable?

    fun dimenInPx(@DimenRes res: Int): Int

}