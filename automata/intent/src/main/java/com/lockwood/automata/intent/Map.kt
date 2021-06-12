package com.lockwood.automata.intent

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.lockwood.automata.core.EMPTY

fun Context.showLocationOnMap(
    lat: String,
    lng: String,
    label: String = String.EMPTY,
): Intent {
    return Intent(Intent.ACTION_VIEW, buildLocationUri(lat, lng, label)).apply {
        startActivity(this)
    }
}

private fun buildLocationUri(
    lat: String,
    lng: String,
    label: String = String.EMPTY,
): Uri {
    val geoLocation = if (label.isEmpty()) {
        "geo:$lat,$lng"
    } else {
        "geo:0,0?q=$lat,$lng($label)"
    }

    return Uri.parse(geoLocation)
}
