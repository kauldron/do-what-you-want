package com.lockwood.automata.res

import com.lockwood.automata.core.ZERO

inline class DrawableRes(val value: Int) : Res {

    companion object {

        val DEFAULT: DrawableRes
            @JvmStatic
            get() = DrawableRes(Int.ZERO)
    }
}