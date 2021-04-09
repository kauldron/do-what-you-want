package com.lockwood.replicant.view.ext

import android.app.Activity
import androidx.fragment.app.Fragment
import com.lockwood.replicant.view.MessageView
import com.lockwood.replicant.view.ProgressView
import com.lockwood.replicant.view.ScreenView

@kotlin.jvm.Throws(IllegalStateException::class)
fun Activity.requireProgressView(): ProgressView = requireActivityType()

@kotlin.jvm.Throws(IllegalStateException::class)
fun Activity.requireScreenView(): ScreenView = requireActivityType()

@kotlin.jvm.Throws(IllegalStateException::class)
fun Activity.requireMessageView(): MessageView = requireActivityType()

@kotlin.jvm.Throws(IllegalStateException::class)
fun Fragment.requireProgressView(): ProgressView = requireActivityType()

@kotlin.jvm.Throws(IllegalStateException::class)
fun Fragment.requireScreenView(): ScreenView = requireActivityType()

@kotlin.jvm.Throws(IllegalStateException::class)
fun Fragment.requireMessageView(): MessageView = requireActivityType()

inline fun <reified T> Activity.requireActivityType(): T {
	require(this is T) { "Activity should implement ${T::class}" }

	return this
}

inline fun <reified T> Fragment.requireActivityType(): T {
	return requireActivity().requireActivityType()
}
