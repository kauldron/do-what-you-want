package com.lockwood.automata.view

import android.view.MotionEvent
import android.widget.TextView

inline fun TextView.setOnTopDrawableClickListener(
    crossinline action: TextView.() -> Unit,
) = setOnCompoundDrawableClickListener(VerticalCompoundDrawableChecker.TOP, action)

inline fun TextView.setOnBottomDrawableClickListener(
    crossinline action: TextView.() -> Unit,
) = setOnCompoundDrawableClickListener(VerticalCompoundDrawableChecker.BOTTOM, action)

inline fun TextView.setOnCompoundDrawableClickListener(
    listener: VerticalCompoundDrawableChecker,
    crossinline action: TextView.() -> Unit,
) = setOnTouchListener { view, event ->
    val isDrawableClicked = listener.isClicked(view as TextView, event)

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
interface VerticalCompoundDrawableChecker {

    fun isClicked(view: TextView, event: MotionEvent): Boolean

    companion object {

        val TOP: VerticalCompoundDrawableChecker
            @JvmStatic
            get() {
                return object : VerticalCompoundDrawableChecker {
                    override fun isClicked(view: TextView, event: MotionEvent): Boolean {
                        val topDrawableOffset = view.height - view.totalPaddingTop
                        return event.y <= topDrawableOffset
                    }
                }
            }

        val BOTTOM: VerticalCompoundDrawableChecker
            @JvmStatic
            get() {
                return object : VerticalCompoundDrawableChecker {
                    override fun isClicked(view: TextView, event: MotionEvent): Boolean {
                        val bottomDrawableOffset = view.height - view.totalPaddingBottom
                        return event.y >= bottomDrawableOffset
                    }
                }
            }
    }
}
