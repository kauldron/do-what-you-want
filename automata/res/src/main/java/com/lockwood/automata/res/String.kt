package com.lockwood.automata.res

import com.lockwood.automata.core.ZERO

inline class StringRes(val value: Int) : Res {

    companion object {

        val DEFAULT: StringRes
            @JvmStatic
            get() = StringRes(Int.ZERO)

        @JvmStatic
        fun Int.toStringRes() = StringRes(this)
    }
}