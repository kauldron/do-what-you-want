package com.lockwood.automata.res.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.lockwood.automata.res.*
import com.lockwood.automata.res.ColorInt.Companion.toColorInt

@Suppress("DEPRECATION")
fun Context.color(
    colorRes: ColorRes,
): Int = if (Build.VERSION.SDK_INT >= 23) {
    getColor(colorRes.value)
} else {
    resources.getColor(colorRes.value)
}

fun Context.drawable(
    drawableRes: DrawableRes,
): Drawable? = getDrawable(drawableRes.value)

fun Context.drawable(
    drawableRes: DrawableRes,
    colorRes: ColorRes,
): Drawable? {
    val drawable = drawable(drawableRes)
    checkNotNull(drawable) {
        return null
    }

    val tintColor = color(colorRes).toColorInt()
    return drawable.apply { setTint(tintColor.value) }
}

fun Context.bitmap(
    bitmapRes: IdRes,
    options: BitmapFactory.Options? = null,
): Bitmap = BitmapFactory.decodeResource(resources, bitmapRes.value, options)

fun Context.anim(
    animRes: AnimRes,
): Animation = AnimationUtils.loadAnimation(this, animRes.value)

fun Context.dimen(
    dimenRes: DimenRes,
): Float = resources.getDimension(dimenRes.value)

fun Context.dimenPx(
    dimenRes: DimenRes,
): Px = resources.getDimensionPixelSize(dimenRes.value).toPx()
