package com.lockwood.automata.android

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.lockwood.automata.core.newInstance

inline fun <reified T : DialogFragment> Fragment.showDialog(
		tag: String,
) = newInstance<T>().show(supportFragmentManager, tag)

inline fun <reified T : DialogFragment> FragmentActivity.showDialog(
		tag: String,
) = newInstance<T>().show(supportFragmentManager, tag)
