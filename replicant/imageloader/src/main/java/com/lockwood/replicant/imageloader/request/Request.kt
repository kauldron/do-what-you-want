package com.lockwood.replicant.imageloader.request

import com.lockwood.replicant.imageloader.options.ImageOptions
import com.lockwood.replicant.imageloader.target.Target

class Request(
    val imageOptions: ImageOptions,
    val imageCallback: Array<Target>,
)