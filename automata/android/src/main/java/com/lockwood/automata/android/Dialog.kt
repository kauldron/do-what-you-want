package com.lockwood.automata.android

import android.app.AlertDialog

inline fun AlertDialog.Builder.setDismissButton(
    text: CharSequence,
    crossinline onClick: () -> Unit,
): AlertDialog.Builder = apply {
    setNegativeButton(text) { dialog, _ ->
        onClick()
        dialog.dismiss()
    }
}