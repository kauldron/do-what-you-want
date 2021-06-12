package com.lockwood.automata.android

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle

@kotlin.jvm.Throws(ActivityNotFoundException::class)
fun Context.launchActivity(
    className: String,
    options: Bundle? = null,
    init: Intent.() -> Unit = {},
) {
    val intent = newIntent(this, className)
    intent.init()

    if (options != null) {
        intent.putExtras(options)
    }

    startActivity(intent)
}

@kotlin.jvm.Throws(ActivityNotFoundException::class)
inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    init: Intent.() -> Unit = {},
) {
    val intent = newIntent<T>(this)
    intent.init()

    if (options != null) {
        intent.putExtras(options)
    }

    startActivity(intent)
}