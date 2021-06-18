@file:Suppress("UNCHECKED_CAST")

package com.lockwood.replicant.imageloader.glide.ext

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder
import com.lockwood.automata.android.dpToPx
import com.lockwood.replicant.imageloader.glide.target.image.GlideDrawableTarget
import com.lockwood.replicant.imageloader.options.CropType
import com.lockwood.replicant.imageloader.options.DpSize
import com.lockwood.replicant.imageloader.options.PixelSize
import com.lockwood.replicant.imageloader.options.Size
import com.lockwood.replicant.imageloader.target.Target

internal fun RequestBuilder<Drawable>.into(target: Target) {
    into(target as GlideDrawableTarget)
}

internal fun <T : Any> RequestBuilder<T>.resize(
    imageSize: Size,
    context: Context,
): RequestBuilder<T> = with(imageSize) {
    if (isUndefined) {
        return this@resize
    }

    return when (this) {
        is PixelSize -> override(width, height)
        is DpSize -> override(context.dpToPx(width), context.dpToPx(height))
    }
}

internal fun <T : Any> RequestBuilder<T>.transform(cropType: CropType): RequestBuilder<T> {
    return when (cropType) {
        CropType.DEFAULT -> this
        CropType.FIT -> fitCenter()
        CropType.CENTER_CROP -> centerCrop()
        CropType.CENTER_INSIDE -> centerInside()
    }
}