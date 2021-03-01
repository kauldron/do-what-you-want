package com.lockwood.automata.res.ext

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import com.lockwood.automata.res.AttrRes
import com.lockwood.automata.res.StyleRes

inline fun View.obtainThemeStyledAttributes(
    attrs: IntArray,
    set: AttributeSet? = null,
    defStyleAttr: AttrRes = AttrRes.DEFAULT,
    defStyleRes: StyleRes = StyleRes.DEFAULT,
    fetch: TypedArray .() -> Unit = {},
) {
    val typedArray = context.theme.obtainStyledAttributes(
        set,
        attrs,
        defStyleAttr.value,
        defStyleRes.value
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