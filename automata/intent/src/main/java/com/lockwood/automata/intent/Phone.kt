package com.lockwood.automata.intent

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.dialPhoneNumber(
    phoneNumber: String,
): Intent {
    return Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber")).apply {
        startActivity(this)
    }
}

fun Context.callPhoneNumber(
    phoneNumber: String,
): Intent {
    return Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber")).apply {
        startActivity(this)
    }
}
