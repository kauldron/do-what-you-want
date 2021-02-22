package com.lockwood.automata.res

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import com.lockwood.automata.core.ZERO

inline fun View.obtainThemeStyledAttributes(
    attrs: IntArray,
    set: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = Int.ZERO,
    @StyleRes defStyleRes: Int = Int.ZERO,
    fetch: TypedArray .() -> Unit = {},
) {
    val typedArray = context.theme.obtainStyledAttributes(
        set,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    with(typedArray) {
        try {
            fetch()
        } finally {
            recycle()
        }
    }
}

inline fun View.obtainStyledAttributes(
    vararg attrs: Int,
    set: AttributeSet? = null,
    fetch: TypedArray .() -> Unit = {},
) {
    val typedArray = context.obtainStyledAttributes(set, attrs)

    with(typedArray) {
        try {
            fetch()
        } finally {
            recycle()
        }
    }
}