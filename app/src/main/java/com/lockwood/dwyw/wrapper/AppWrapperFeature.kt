package com.lockwood.dwyw.wrapper

import com.lockwood.automata.core.notSafeLazy
import com.lockwood.dwyw.core.wrapper.BuildConfigWrapper
import com.lockwood.dwyw.core.wrapper.WrapperFeature

class AppWrapperFeature : WrapperFeature {

	override val buildConfigWrapper: BuildConfigWrapper by notSafeLazy {
		BuildConfigWrapperImpl()
	}
}