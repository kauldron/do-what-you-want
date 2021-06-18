package com.lockwood.replicant.imageloader.target

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.CallSuper

interface ImageViewTarget : ViewTarget<ImageView> {

    @CallSuper
    override fun onStart(placeholder: Drawable) {
        targetView.setImageDrawable(placeholder)
    }

    @CallSuper
    override fun onSuccess(result: Drawable) {
        targetView.setImageDrawable(result)
    }

    @CallSuper
    override fun onError(error: Drawable) {
        targetView.setImageDrawable(error)
    }

}
