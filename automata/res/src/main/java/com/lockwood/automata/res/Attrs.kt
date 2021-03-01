package com.lockwood.automata.res

import com.lockwood.automata.core.ZERO

inline class IdRes(val value: Int) : Res {

    companion object {

        val DEFAULT: IdRes
            @JvmStatic
            get() = IdRes(Int.ZERO)

        @JvmStatic
        fun Int.toIdRes() = IdRes(this)
    }
}

inline class StyleRes(val value: Int) : Res {

    companion object {

        val DEFAULT: StyleRes
            @JvmStatic
            get() = StyleRes(Int.ZERO)

        @JvmStatic
        fun Int.toStyleRes() = StyleRes(this)
    }
}

inline class AttrRes(val value: Int) : Res {

    companion object {

        val DEFAULT: AttrRes
            @JvmStatic
            get() = AttrRes(Int.ZERO)

        @JvmStatic
        fun Int.toAttrRes() = AttrRes(this)
    }
}

inline class DimenRes(val value: Int) : Res {

    companion object {

        val DEFAULT: DimenRes
            @JvmStatic
            get() = DimenRes(Int.ZERO)

        @JvmStatic
        fun Int.toDimenRes() = DimenRes(this)
    }
}