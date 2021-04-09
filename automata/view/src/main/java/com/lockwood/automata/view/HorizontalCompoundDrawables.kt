package com.lockwood.automata.view

import android.view.MotionEvent
import android.widget.TextView
import com.lockwood.automata.android.isRightToLeft

inline fun TextView.setOnStartDrawableClickListener(
		crossinline action: TextView.() -> Unit,
) = setOnCompoundDrawableClickListener(HorizontalCompoundDrawableChecker.START, action)

inline fun TextView.setOnEndDrawableClickListener(
		crossinline action: TextView.() -> Unit,
) = setOnCompoundDrawableClickListener(HorizontalCompoundDrawableChecker.END, action)

inline fun TextView.setOnCompoundDrawableClickListener(
		listener: HorizontalCompoundDrawableChecker,
		crossinline action: TextView.() -> Unit,
) = setOnTouchListener { view, event ->
	val isDrawableClicked = listener.isClicked(view as TextView, event, context.isRightToLeft)

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
					): Boolean =
							if (isRtl) {
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
					): Boolean =
							if (isRtl) {
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
