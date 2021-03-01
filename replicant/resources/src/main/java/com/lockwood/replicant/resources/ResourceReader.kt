package com.lockwood.replicant.resources

import android.graphics.drawable.Drawable
import com.lockwood.automata.res.*

interface ResourceReader {

    fun color(res: ColorRes): Int

    fun string(res: StringRes): String

    fun string(res: StringRes, vararg args: String): String

    fun drawable(res: DrawableRes): Drawable?

    fun dimenInPx(res: DimenRes): Px

}