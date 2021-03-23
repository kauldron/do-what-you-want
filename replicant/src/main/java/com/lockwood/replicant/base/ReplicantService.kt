package com.lockwood.replicant.base

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.lockwood.replicant.ext.getFeature
import com.lockwood.replicant.ext.releaseFeature
import com.lockwood.replicant.feature.Feature
import com.lockwood.replicant.feature.ReleasableFeature

abstract class ReplicantService : Service() {

  override fun onBind(intent: Intent?): IBinder? = null

  protected inline fun <reified T : Feature> getFeature(): T {
    return application.getFeature()
  }

  protected inline fun <reified T : ReleasableFeature> releaseFeature() {
    application.releaseFeature<T>()
  }
}
