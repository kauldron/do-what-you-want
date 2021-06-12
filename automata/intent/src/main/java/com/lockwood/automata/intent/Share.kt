package com.lockwood.automata.intent

import android.content.Context
import android.content.Intent

fun Context.shareText(
    text: String,
): Intent {
    return Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)

        val chooserIntent = Intent.createChooser(this, null)
        startActivity(chooserIntent)
    }
}
