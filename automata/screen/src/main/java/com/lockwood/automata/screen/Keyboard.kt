package com.lockwood.automata.screen

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.lockwood.automata.android.getSystemService
import com.lockwood.automata.core.ZERO

fun Fragment.hideKeyboard() {
    val view = requireNotNull(view).rootView
    requireNotNull(view) {
        return
    }

    view.hideKeyboard()
}

fun Activity.hideKeyboard() {
    val view = currentFocus
    requireNotNull(view) {
        return
    }

    view.hideKeyboard()
}

private fun View.hideKeyboard() {
    val inputMethodManager = requireNotNull(context.getSystemService<InputMethodManager>())
    inputMethodManager.hideSoftInputFromWindow(windowToken, Int.ZERO)

    clearFocus()
}