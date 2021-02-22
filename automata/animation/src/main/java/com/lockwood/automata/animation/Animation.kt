package com.lockwood.automata.animation

import android.os.Build
import android.transition.AutoTransition
import android.transition.Transition
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat


inline fun View.animate(
    init: ViewPropertyAnimatorCompat.() -> Unit,
) {
    ViewCompat.animate(this).apply {
        init()
    }
}

inline fun View.startAnimation(
    animation: () -> Unit,
) {
    clearAnimation()
    animation()
}

@RequiresApi(Build.VERSION_CODES.KITKAT)
fun View.beginDelayedTransition(
    transition: Transition = AutoTransition(),
) {
    try {
        TransitionManager.beginDelayedTransition(rootView as ViewGroup, transition)
    } catch (e: Throwable) {
        Log.e("Animation", "Check is rootView is ViewGroup for this view")
    }
}