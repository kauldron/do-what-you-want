package com.lockwood.automata.android

import android.app.Application
import android.app.Fragment
import android.os.Bundle
import com.lockwood.automata.core.newInstance

val Fragment.application: Application
    get() = context.applicationContext as Application

inline fun <reified T : Fragment> newFragment(): T {
    return newInstance()
}

inline fun <reified T : Fragment> newFragment(
    noinline init: Bundle.() -> Unit,
): T {
    val fragment = newInstance<T>()

    val args = Bundle()
    args.init()
    fragment.arguments = args

    return fragment
}
