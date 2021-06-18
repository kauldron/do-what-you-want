package com.lockwood.replicant.imageloader.request

import com.lockwood.replicant.imageloader.options.ImageOptions
import com.lockwood.replicant.imageloader.options.RequestOptions
import com.lockwood.replicant.imageloader.target.Target

class Request(
    @JvmField
    val imageOptions: ImageOptions,
    @JvmField
    val requestOptions: RequestOptions,
    @JvmField
    val imageCallback: Array<Target>,
)
