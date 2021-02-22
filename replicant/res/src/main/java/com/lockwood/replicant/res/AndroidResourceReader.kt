package com.lockwood.replicant.res

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.lockwood.automata.res.color
import com.lockwood.automata.res.dimenPx
import com.lockwood.automata.res.drawable

class AndroidResourceReader(
    private val context: Context,
) : ResourceReader {

    override fun color(@ColorRes res: Int) = context.color(res)

    override fun string(@StringRes res: Int) = context.getString(res)

    override fun string(@StringRes res: Int, vararg args: String) = context.getString(res, args)

    override fun drawable(@DrawableRes res: Int) = context.drawable(res)

    override fun dimenInPx(@DimenRes res: Int) = context.dimenPx(res)

}