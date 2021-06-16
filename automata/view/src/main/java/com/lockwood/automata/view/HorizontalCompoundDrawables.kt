package com.lockwood.automata.view

import android.view.MotionEvent
import android.widget.TextView

inline fun TextView.setOnStartDrawableClickListener(
    isRightToLeft: Boolean = false,
    crossinline action: TextView.() -> Unit,
) = setOnCompoundDrawableClickListener(
    HorizontalCompoundDrawableChecker.START,
    isRightToLeft,
    action
)

inline fun TextView.setOnEndDrawableClickListener(
    isRightToLeft: Boolean = false,
    crossinline action: TextView.() -> Unit,
) = setOnCompoundDrawableClickListener(
    HorizontalCompoundDrawableChecker.END,
    isRightToLeft,
    action
)

inline fun TextView.setOnCompoundDrawableClickListener(
    listener: HorizontalCompoundDrawableChecker,
    isRightToLeft: Boolean = false,
    crossinline action: TextView.() -> Unit,
) = setOnTouchListener { view, event ->
    val isDrawableClicked = listener.isClicked(view as TextView, event, isRightToLeft)

    if (isDrawableClicked) {
        if (event.action == MotionEvent.ACTION_UP) {
            action()
        }

        view.performClick()
        return@setOnTouchListener true
    }

    return@setOnTouchListener false
}

@FunctionalInterface
interface HorizontalCompoundDrawableChecker {

    fun isClicked(view: TextView, event: MotionEvent, isRtl: Boolean): Boolean

    companion object {

        val START: HorizontalCompoundDrawableChecker
            @JvmStatic
            get() {
                return object : HorizontalCompoundDrawableChecker {
                    override fun isClicked(
                        view: TextView,
                        event: MotionEvent,
                        isRtl: Boolean,
                    ): Boolean = if (isRtl) {
                        isEndDrawableClicked(view, event)
                    } else {
                        isStartDrawableClicked(view, event)
                    }
                }
            }

        val END: HorizontalCompoundDrawableChecker
            @JvmStatic
            get() {
                return object : HorizontalCompoundDrawableChecker {
                    override fun isClicked(
                        view: TextView,
                        event: MotionEvent,
                        isRtl: Boolean,
                    ): Boolean = if (isRtl) {
                        isStartDrawableClicked(view, event)
                    } else {
                        isEndDrawableClicked(view, event)
                    }
                }
            }

        private fun isStartDrawableClicked(view: TextView, event: MotionEvent): Boolean {
            val leftDrawableOffset = view.width - view.totalPaddingLeft
            return event.x <= leftDrawableOffset
        }

        private fun isEndDrawableClicked(view: TextView, event: MotionEvent): Boolean {
            val rightDrawableOffset = view.width - view.totalPaddingRight
            return event.x >= rightDrawableOffset
        }
    }
}
