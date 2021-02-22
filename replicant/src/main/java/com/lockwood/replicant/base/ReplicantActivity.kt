package com.lockwood.replicant.base

import androidx.appcompat.app.AppCompatActivity
import com.lockwood.automata.android.handleIRequestFinishCallbackMemoryLeak
import com.lockwood.replicant.ext.getFeature
import com.lockwood.replicant.ext.releaseFeature
import com.lockwood.replicant.feature.Feature
import com.lockwood.replicant.feature.ReleasableFeature
import com.lockwood.replicant.screen.Screen
import com.lockwood.replicant.view.ScreenView

abstract class ReplicantActivity : AppCompatActivity(), ScreenView {

    override fun onBackPressed() {
        handleIRequestFinishCallbackMemoryLeak()
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun showScreen(screen: Screen) {
        error("Unknown screen: $screen")
    }

    inline fun <reified T : Feature> getFeature(): T {
        return application.getFeature()
    }

    protected inline fun <reified T : ReleasableFeature> releaseFeature() {
        application.releaseFeature<T>()
    }
}