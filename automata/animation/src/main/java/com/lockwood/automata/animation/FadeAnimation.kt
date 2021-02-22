package com.lockwood.automata.animation

import android.view.View

fun View.fadeOut(
    duration: Long = FADE_DURATION,
) = animate {
    alpha(VISIBILITY_INVISIBLE)
    setDuration(duration)

    withEndAction {
        visibility = View.INVISIBLE
    }
}

fun View.fadeIn(
    duration: Long = FADE_DURATION,
) {
    visibility = View.VISIBLE
    alpha = VISIBILITY_INVISIBLE

    animate {
        alpha(VISIBILITY_VISIBLE)
        setDuration(duration)
    }
}

fun View.updateVisibilityFade(
    isVisible: Boolean = false,
) = if (isVisible) {
    fadeIn(FADE_VISIBILITY_DURATION)
} else {
    fadeOut(FADE_VISIBILITY_DURATION)
}


