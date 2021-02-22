package com.lockwood.automata.android

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lockwood.automata.core.ZERO

@kotlin.jvm.Throws(ActivityNotFoundException::class)
fun Context.launchActivity(
    className: String,
    options: Bundle? = null,
    init: Intent.() -> Unit = {},
) {
    val intent = newIntent(this, className)
    intent.init()

    startActivity(intent, options)
}

@kotlin.jvm.Throws(ActivityNotFoundException::class)
inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    init: Intent.() -> Unit = {},
) {
    val intent = newIntent<T>(this)
    intent.init()

    startActivity(intent, options)
}

// Workaround for [Android Q Beta] Memory leak in IRequestFinishCallback$Stub
// https://issuetracker.google.com/issues/139738913
fun AppCompatActivity.handleIRequestFinishCallbackMemoryLeak() {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        if (isTaskRoot && supportFragmentManager.backStackEntryCount == Int.ZERO) {
            finishAfterTransition()
        }
    } else {
        onBackPressed()
    }
}