package com.lockwood.automata.intent

import android.Manifest.permission.CALL_PHONE
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.RequiresPermission

fun Context.dialPhoneNumber(
		phoneNumber: String,
): Intent {
	return Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber")).apply {
		startActivity(this)
	}
}

@RequiresPermission(CALL_PHONE)
fun Context.callPhoneNumber(
		phoneNumber: String,
): Intent {
	return Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber")).apply {
		startActivity(this)
	}
}
