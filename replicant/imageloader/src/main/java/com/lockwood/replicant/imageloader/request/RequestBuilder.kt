package com.lockwood.replicant.imageloader.request

import com.lockwood.replicant.imageloader.options.ImageOptions
import com.lockwood.replicant.imageloader.options.RequestOptions
import com.lockwood.replicant.imageloader.target.Target

class RequestBuilder {

    private var imageOptions: ImageOptions = ImageOptions()
    private var requestOptions: RequestOptions = RequestOptions()

    private val imageCallback: MutableList<Target> = mutableListOf()

    fun image(init: ImageOptions.() -> Unit) {
        imageOptions = imageOptions.apply(init)
    }

    fun request(init: RequestOptions.() -> Unit) {
        requestOptions = requestOptions.apply(init)
    }

    fun addCallback(target: Target) {
        imageCallback.add(target)
    }

    fun build(): Request {
        return Request(
            imageOptions = imageOptions,
            requestOptions = requestOptions,
            imageCallback = imageCallback.toTypedArray()
        )
    }
}

fun buildImageRequest(init: RequestBuilder.() -> Unit): Request {
    return RequestBuilder().apply(init).build()
}
