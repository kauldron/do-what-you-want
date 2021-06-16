package com.lockwood.automata.android

import android.content.Context
import android.content.Intent

fun newIntent(
    context: Context,
    className: String,
    packageName: String = context.packageName,
): Intent = Intent().apply { setClassName(packageName, className) }

inline fun <reified T : Any> newIntent(
    context: Context,
): Intent = Intent(context, T::class.java)