package com.lockwood.replicant.imageloader.options

import android.graphics.drawable.Drawable

class RequestOptions {
    @JvmField
    var placeholder: Drawable? = null

    @JvmField
    var error: Drawable? = null

    @JvmField
    var fallback: Drawable? = null
}
