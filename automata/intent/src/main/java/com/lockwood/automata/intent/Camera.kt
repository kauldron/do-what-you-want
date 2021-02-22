package com.lockwood.automata.intent

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import com.lockwood.automata.android.buildIntent

fun Activity.capturePhoto(
    uri: Uri,
    requestCode: Int,
) = startCameraAction(
    uri = uri,
    requestCode = requestCode,
    action = MediaStore.ACTION_IMAGE_CAPTURE,
)

fun Activity.captureStillImage(
    uri: Uri,
    requestCode: Int,
) = startCameraAction(
    uri = uri,
    requestCode = requestCode,
    action = MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA,
)

fun Activity.captureVideo(
    uri: Uri,
    requestCode: Int,
) = startCameraAction(
    uri = uri,
    requestCode = requestCode,
    action = MediaStore.INTENT_ACTION_VIDEO_CAMERA
)

private fun Activity.startCameraAction(
    uri: Uri,
    action: String,
    requestCode: Int,
) = buildIntent(action) {
    putExtra(MediaStore.EXTRA_OUTPUT, uri)

    startActivityForResult(intent, requestCode)
    return@buildIntent
}