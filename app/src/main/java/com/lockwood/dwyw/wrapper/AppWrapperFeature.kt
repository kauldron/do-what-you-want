package com.lockwood.dwyw.wrapper

import com.lockwood.automata.core.notSafeLazy
import com.lockwood.dwyw.core.feature.wrapper.WrapperFeature
import com.lockwood.dwyw.core.wrapper.BuildConfigWrapper

class AppWrapperFeature : WrapperFeature {

    override val buildConfigWrapper: BuildConfigWrapper by notSafeLazy {
        BuildConfigWrapperImpl()
    }

}