package com.lockwood.replicant.feature.base

import androidx.annotation.MainThread
import androidx.fragment.app.FragmentActivity
import com.lockwood.replicant.feature.Feature
import com.lockwood.replicant.feature.ReleasableFeature
import com.lockwood.replicant.feature.ext.getFeature
import com.lockwood.replicant.feature.ext.releaseFeature

abstract class FeatureActivity : FragmentActivity() {

	@MainThread
	protected inline fun <reified T : Feature> getFeature(): T {
		return application.getFeature()
	}

	@MainThread
	protected inline fun <reified T : ReleasableFeature> releaseFeature() {
		application.releaseFeature<T>()
	}

}