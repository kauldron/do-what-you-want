package com.lockwood.automata.android

import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

val Fragment.supportFragmentManager: FragmentManager
	get() = requireActivity().supportFragmentManager

inline fun FragmentManager.addOnBackStackChangedListener(
		crossinline action: (Int, List<Fragment>) -> Unit,
) {
	addOnBackStackChangedListener { action(backStackEntryCount, fragments) }
}

fun FragmentManager.clearBackStack() {
	for (fragment in fragments) {
		transact { remove(fragment) }
	}
}

fun FragmentManager.showFragmentViaBackStack(
		@IdRes container: Int,
		fragment: Fragment,
		tag: String = requireNotNull(fragment::class.simpleName),
		action: FragmentTransaction.() -> Unit = {},
) {
	val fragmentPopped = popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
	if (!fragmentPopped) {
		transact {
			action()
			replace(container, fragment, tag)
			addToBackStack(tag)
		}
	}
}

inline fun <reified T : Fragment> FragmentManager.showFragment(
		@IdRes container: Int,
		fragment: Fragment,
		tag: String = requireNotNull(T::class.simpleName),
		action: FragmentTransaction.() -> Unit = {},
) {
	transact {
		action()
		replace(container, fragment, tag)
	}
}

inline fun FragmentManager.transact(
		action: FragmentTransaction.() -> Unit,
) {
	try {
		beginTransaction().apply { action() }.commit()
	} catch (e: IllegalStateException) {
		Log.e("FragmentManager", "transact: ${e.message}")
	}
}

inline fun FragmentManager.transactNow(
		action: FragmentTransaction.() -> Unit,
) {
	try {
		beginTransaction().apply { action() }.commitNow()
	} catch (e: IllegalStateException) {
		Log.e("FragmentManager", "transactNow: ${e.message}")
	}
}

inline fun FragmentManager.transactAllowingStateLoss(
		action: FragmentTransaction.() -> Unit,
) {
	try {
		beginTransaction().apply { action() }.commitAllowingStateLoss()
	} catch (e: IllegalStateException) {
		Log.e("FragmentManager", "transactAllowingStateLoss: ${e.message}")
	}
}

inline fun <reified T> FragmentManager.requireFragmentType(@IdRes container: Int): T {
	val currentFragment = findFragmentById(container)
	require(currentFragment is T) { "Fragment should implement ${T::class}" }

	return currentFragment
}

