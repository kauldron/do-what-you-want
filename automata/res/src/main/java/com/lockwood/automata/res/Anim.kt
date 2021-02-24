package com.lockwood.automata.res

import com.lockwood.automata.core.ZERO

inline class AnimRes(val value: Int) : Res {

    companion object {

        val DEFAULT: AnimRes
            @JvmStatic
            get() = AnimRes(Int.ZERO)
    }
}