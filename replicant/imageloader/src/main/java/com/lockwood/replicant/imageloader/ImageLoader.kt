package com.lockwood.replicant.imageloader

import android.view.View
import com.lockwood.replicant.imageloader.request.Request
import com.lockwood.replicant.imageloader.target.Target
import com.lockwood.replicant.imageloader.target.ViewTarget

interface ImageLoader {

    fun <V : View> execute(request: Request, view: V)

    fun <T : View> buildViewTarget(view: T, callbacks: Array<Target>): ViewTarget<T>

}