package com.lockwood.replicant.feature.base

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import com.lockwood.replicant.feature.Feature
import com.lockwood.replicant.feature.ReleasableFeature
import com.lockwood.replicant.feature.ext.getFeature
import com.lockwood.replicant.feature.ext.releaseFeature

abstract class FeatureFragment : Fragment() {

	@MainThread
	protected inline fun <reified T : Feature> getFeature(): T {
		return requireActivity().application.getFeature()
	}

	@MainThread
	protected inline fun <reified T : ReleasableFeature> releaseFeature() {
		requireActivity().application.releaseFeature<T>()
	}

}