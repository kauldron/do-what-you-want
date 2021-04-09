package com.lockwood.automata.view

import android.graphics.drawable.Drawable
import android.widget.TextView

fun TextView.updateCompoundDrawables(
		start: Drawable? = null,
		top: Drawable? = null,
		end: Drawable? = null,
		bottom: Drawable? = null,
) = setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
