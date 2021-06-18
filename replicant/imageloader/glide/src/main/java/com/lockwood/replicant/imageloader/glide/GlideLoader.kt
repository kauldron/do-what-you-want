@file:Suppress("UNCHECKED_CAST")

package com.lockwood.replicant.imageloader.glide

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.lockwood.automata.android.ApplicationContext
import com.lockwood.replicant.imageloader.ImageLoader
import com.lockwood.replicant.imageloader.glide.ext.into
import com.lockwood.replicant.imageloader.glide.ext.resize
import com.lockwood.replicant.imageloader.glide.ext.transform
import com.lockwood.replicant.imageloader.glide.target.GlideImageTarget
import com.lockwood.replicant.imageloader.request.Request
import com.lockwood.replicant.imageloader.target.Target
import com.lockwood.replicant.imageloader.target.ViewTarget

class GlideLoader(
    private val context: ApplicationContext,
) : ImageLoader {

    private val loader: RequestManager = Glide.with(context.application)

    @Suppress("UNCHECKED_CAST")
    override fun <V : View> execute(request: Request, view: V) = with(request) {
        val target: Target = buildViewTarget(view, imageCallback)

        loader
            .load(imageOptions.data)
            .resize(imageOptions.size, context.application)
            .transform(imageOptions.cropType)
            .into(target)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : View> buildViewTarget(
        view: T,
        callbacks: Array<Target>,
    ): ViewTarget<T> {
        return when (view) {
            is ImageView -> GlideImageTarget(view, *callbacks) as ViewTarget<T>
            else -> error("Unknown view for target: $view")
        }
    }

}
