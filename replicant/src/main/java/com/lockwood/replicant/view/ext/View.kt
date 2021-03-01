package com.lockwood.replicant.view.ext

import androidx.fragment.app.Fragment
import com.lockwood.replicant.view.ProgressView
import com.lockwood.replicant.view.ScreenView

@kotlin.jvm.Throws(IllegalStateException::class)
fun Fragment.requireProgressView(): ProgressView {
    check(requireActivity() is ProgressView) {
        "Activity should implement ProgressView"
    }
    return (requireActivity() as ProgressView)
}

@kotlin.jvm.Throws(IllegalStateException::class)
fun Fragment.requireScreenView(): ScreenView {
    check(requireActivity() is ScreenView) {
        "Activity should implement ScreenView"
    }
    return (requireActivity() as ScreenView)
}