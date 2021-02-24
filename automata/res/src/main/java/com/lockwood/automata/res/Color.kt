package com.lockwood.automata.res

import com.lockwood.automata.core.ZERO

typealias ColorsIntArray = Array<ColorInt>

inline class ColorInt(val value: Int) {

    companion object {

        @JvmStatic
        fun Int.toColorInt() = ColorInt(this)

        @JvmStatic
        fun ColorsIntArray.toIntArray(): IntArray = map { it.value }.toIntArray()
    }
}

inline class ColorRes(val value: Int) : Res {

    companion object {

        val DEFAULT: ColorRes
            @JvmStatic
            get() = ColorRes(Int.ZERO)

        @JvmStatic
        fun Int.toColorRes() = ColorRes(this)
    }
}