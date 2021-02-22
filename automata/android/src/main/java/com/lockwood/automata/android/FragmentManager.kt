package com.lockwood.automata.android

import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.lockwood.automata.core.ZERO

val Fragment.supportFragmentManager: FragmentManager
    get() = requireActivity().supportFragmentManager

inline fun FragmentManager.addOnBackStackChangedListener(
    crossinline action: (Int, List<Fragment>) -> Unit,
) {
    addOnBackStackChangedListener {
        action(backStackEntryCount, fragments)
    }
}

fun FragmentManager.replaceFragmentWithBackStack(
    @IdRes container: Int,
    fragment: Fragment,
    tag: String = requireNotNull(fragment::class.simpleName),
) {
    val fragmentPopped = popBackStackImmediate(tag, Int.ZERO)
    if (!fragmentPopped) {
        transact {
            replace(container, fragment, tag)
            addToBackStack(tag)
        }
    }
}

inline fun FragmentManager.transact(
    action: FragmentTransaction.() -> Unit,
) {
    try {
        beginTransaction().apply {
            action()
        }.commit()
    } catch (e: IllegalStateException) {
        Log.e("FragmentManager", "transact: ${e.message}")
    }
}

inline fun FragmentManager.transactNow(
    action: FragmentTransaction.() -> Unit,
) {
    try {
        beginTransaction().apply {
            action()
        }.commitNow()
    } catch (e: IllegalStateException) {
        Log.e("FragmentManager", "transactNow: ${e.message}")
    }
}

inline fun FragmentManager.transactAllowingStateLoss(
    action: FragmentTransaction.() -> Unit,
) {
    try {
        beginTransaction().apply {
            action()
        }.commitAllowingStateLoss()
    } catch (e: IllegalStateException) {
        Log.e("FragmentManager", "transactAllowingStateLoss: ${e.message}")
    }
}