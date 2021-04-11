package com.lockwood.dwyw.core.feature.wrapper

import com.lockwood.dwyw.core.wrapper.BuildConfigWrapper
import com.lockwood.replicant.feature.Feature

interface WrapperFeature : Feature {

	val buildConfigWrapper: BuildConfigWrapper

}
