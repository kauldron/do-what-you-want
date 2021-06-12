package com.lockwood.replicant.feature.base

import android.app.Activity
import androidx.annotation.MainThread
import com.lockwood.replicant.feature.Feature
import com.lockwood.replicant.feature.ReleasableFeature
import com.lockwood.replicant.feature.ext.getFeature
import com.lockwood.replicant.feature.ext.releaseFeature

abstract class FeatureActivity : Activity() {

    @MainThread
    protected inline fun <reified T : Feature> getFeature(): T {
        return application.getFeature()
    }

    @MainThread
    protected inline fun <reified T : ReleasableFeature> releaseFeature() {
        application.releaseFeature<T>()
    }

}