package com.lockwood.automata.view

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import com.lockwood.automata.core.ZERO
import com.lockwood.automata.screen.screenHeight
import com.lockwood.automata.screen.screenWidth

val View.isVisibleOnScreen: Boolean
    get() {
        if (!isShown) {
            return false
        }

        val actualPosition = Rect()
        getGlobalVisibleRect(actualPosition)

        val activity = (context as Activity)

        val screen = Rect(Int.ZERO, Int.ZERO, activity.screenWidth, activity.screenHeight)
        return actualPosition.intersect(screen)
    }

inline fun <T : View> T.afterMeasured(
    crossinline f: T.() -> Unit,
) {
    viewTreeObserver.addOnGlobalLayoutListener {
        if (measuredWidth > Int.ZERO && measuredHeight > Int.ZERO) {
            f()
        }
    }
}

inline fun <T : View> T.afterMeasuredOnce(
    crossinline f: T.() -> Unit,
) {
    viewTreeObserver.addOnGlobalLayoutListener(
        object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > Int.ZERO && measuredHeight > Int.ZERO) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    f()
                }
            }
        }
    )
}
