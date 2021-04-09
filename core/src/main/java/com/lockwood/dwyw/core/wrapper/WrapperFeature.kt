package com.lockwood.dwyw.core.wrapper

import com.lockwood.replicant.feature.Feature

interface WrapperFeature : Feature {

	val buildConfigWrapper: BuildConfigWrapper

}
