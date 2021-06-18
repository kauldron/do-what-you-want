package com.lockwood.replicant.imageloader.glide.target

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.transition.Transition
import com.lockwood.replicant.imageloader.glide.target.image.GlideImageCustomTarget
import com.lockwood.replicant.imageloader.target.ImageViewTarget
import com.lockwood.replicant.imageloader.target.Target

internal class GlideImageTarget(
    override val targetView: ImageView,
    private vararg val callbacks: Target,
) : GlideImageCustomTarget(targetView), ImageViewTarget {

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        onSuccess(resource)
        callbacks.iterator().forEach { target -> target.onSuccess(resource) }
    }

    override fun onResourceCleared(placeholder: Drawable?) {
        checkNotNull(placeholder) {
            return
        }

        onStart(placeholder)
        callbacks.iterator().forEach { target -> target.onStart(placeholder) }
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        checkNotNull(errorDrawable) {
            return
        }

        onError(errorDrawable)
        callbacks.iterator().forEach { target -> target.onError(errorDrawable) }
    }

}
