package com.lockwood.automata.android

import android.app.Activity
import android.app.DialogFragment
import android.app.Fragment
import com.lockwood.automata.core.newInstance

inline fun <reified T : DialogFragment> Fragment.showDialog(
    tag: String,
) = newInstance<T>().show(fragmentManager, tag)

inline fun <reified T : DialogFragment> Activity.showDialog(
    tag: String,
) = newInstance<T>().show(fragmentManager, tag)
