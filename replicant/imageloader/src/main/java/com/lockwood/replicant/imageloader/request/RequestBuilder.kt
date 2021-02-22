package com.lockwood.replicant.imageloader.request

import com.lockwood.replicant.imageloader.options.ImageOptions
import com.lockwood.replicant.imageloader.target.Target

class RequestBuilder() {

	private var imageOptions: ImageOptions? = null

	private val imageCallback: MutableList<Target> = mutableListOf()

	fun image(init: ImageOptions.() -> Unit) {
		imageOptions = ImageOptions().apply { init() }
	}

	fun image(init: Any?) {
		image { data = init }
	}

	fun addCallback(target: Target) {
		imageCallback.add(target)
	}

	fun build(): Request = Request(
		imageOptions = checkNotNull(imageOptions),
		imageCallback = imageCallback.toTypedArray()
	)

}

fun buildImageRequest(init: RequestBuilder.() -> Unit): Request {
	return RequestBuilder().apply(init).build()
}

fun buildImageRequest(data: String, init: RequestBuilder.() -> Unit = {}): Request {
	return RequestBuilder().apply(init).apply { image(data) }.build()
}