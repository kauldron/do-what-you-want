package com.lockwood.replicant.imageloader.glide

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.lockwood.replicant.imageloader.request.Request
import com.lockwood.replicant.imageloader.target.Target
import com.lockwood.replicant.imageloader.target.ViewTarget
import com.lockwood.replicant.imageloader.ImageLoader
import com.lockwood.replicant.imageloader.glide.target.GlideImageTarget
import com.lockwood.replicant.imageloader.glide.target.image.GlideDrawableTarget

class GlideLoader(context: Context) : ImageLoader {

	private val loader: RequestManager = Glide.with(context.applicationContext)

	@Suppress("UNCHECKED_CAST")
	override fun <V : View> execute(request: Request, view: V): Unit = with(request) {
		loader.load(imageOptions.data)
			.override(imageOptions.size)
			.centerCrop()
			.into(buildViewTarget(view, imageCallback))
	}

	@Suppress("UNCHECKED_CAST")
	override fun <T : View> buildViewTarget(view: T, callbacks: Array<Target>): ViewTarget<T> {
		return when (view) {
			is ImageView -> GlideImageTarget(view, *callbacks) as ViewTarget<T>
			else -> error("Unknown view for target: $view")
		}
	}

	private fun RequestBuilder<Drawable>.into(target: Target?): GlideDrawableTarget {
		checkNotNull(target)

		return when (target) {
			is GlideImageTarget -> into(target)
			else -> error("Unknown target: $target")
		}
	}

	private fun <T : Any> RequestBuilder<T>.override(imageSize: Int?): RequestBuilder<T> {
		return if (imageSize != null) {
			apply { override(imageSize) }
		} else {
			this
		}
	}

}


