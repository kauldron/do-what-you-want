package com.lockwood.replicant.base

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.annotation.MainThread
import com.lockwood.replicant.feature.Feature
import com.lockwood.replicant.feature.ReleasableFeature
import com.lockwood.replicant.feature.ext.getFeature
import com.lockwood.replicant.feature.ext.releaseFeature

abstract class ReplicantService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    @MainThread
    protected inline fun <reified T : Feature> getFeature(): T {
        return application.getFeature()
    }

    @MainThread
    protected inline fun <reified T : ReleasableFeature> releaseFeature() {
        application.releaseFeature<T>()
    }
}
