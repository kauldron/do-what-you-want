package com.lockwood.replicant.base

import androidx.fragment.app.FragmentActivity
import com.lockwood.automata.android.handleIRequestFinishCallbackMemoryLeak
import com.lockwood.replicant.ext.getFeature
import com.lockwood.replicant.ext.releaseFeature
import com.lockwood.replicant.feature.Feature
import com.lockwood.replicant.feature.ReleasableFeature
import com.lockwood.replicant.view.ScreenView

abstract class ReplicantActivity : FragmentActivity(), ScreenView {

    override fun onBackPressed() {
        handleIRequestFinishCallbackMemoryLeak()
    }

    override fun goBack() {
        onBackPressed()
    }

    inline fun <reified T : Feature> getFeature(): T {
        return application.getFeature()
    }

    protected inline fun <reified T : ReleasableFeature> releaseFeature() {
        application.releaseFeature<T>()
    }
}