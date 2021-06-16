package com.lockwood.automata.view

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.annotation.StyleRes

@Suppress("DEPRECATION")
fun String.fromHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(this)
    }
}

fun TextView.setHtmlText(
    htmlText: String,
    enableLinkMovement: Boolean = false,
) {
    text = htmlText.fromHtml()
    enableLinkMovement(enableLinkMovement)
}

fun TextView.enableLinkMovement(
    isEnable: Boolean,
) {
    movementMethod = if (isEnable) {
        LinkMovementMethod.getInstance()
    } else {
        null
    }
}

@Suppress("DEPRECATION")
fun TextView.setAppearance(
    @StyleRes style: Int,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(style)
    } else {
        setTextAppearance(context, style)
    }
}
