package com.lockwood.replicant.imageloader.options

import androidx.annotation.Dimension
import androidx.annotation.Px
import com.lockwood.automata.core.UNDEFINED

sealed interface Size {

    val width: Int
    val height: Int

    val isUndefined: Boolean
        get() = width == Int.UNDEFINED || height == Int.UNDEFINED
}

class PixelSize(
    @Px
    override val width: Int,
    @Px
    override val height: Int = width,
) : Size {

    companion object {

        @JvmField
        val UNDEFINED = PixelSize(Int.UNDEFINED)
    }
}

class DpSize(
    @Dimension
    override val width: Int,
    @Dimension
    override val height: Int = width,
) : Size {

    companion object {

        @JvmField
        val UNDEFINED = PixelSize(Int.UNDEFINED)
    }
}