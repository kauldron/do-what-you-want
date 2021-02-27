package com.lockwood.replicant.resources

import android.content.Context
import com.lockwood.automata.res.ColorRes
import com.lockwood.automata.res.DimenRes
import com.lockwood.automata.res.DrawableRes
import com.lockwood.automata.res.StringRes
import com.lockwood.automata.res.ext.color
import com.lockwood.automata.res.ext.dimenPx
import com.lockwood.automata.res.ext.drawable

class AndroidResourceReader(
    private val context: Context,
) : ResourceReader {

    override fun color(res: ColorRes) = context.color(res)

    override fun string(res: StringRes) = context.getString(res.value)

    override fun string(res: StringRes, vararg args: String) = context.getString(res.value, args)

    override fun drawable(res: DrawableRes) = context.drawable(res)

    override fun dimenInPx(res: DimenRes) = context.dimenPx(res)

}