package com.lockwood.replicant.base

import com.lockwood.replicant.feature.base.FeatureActivity
import com.lockwood.replicant.screen.Screen
import com.lockwood.replicant.view.ScreenView

abstract class ScreenActivity : FeatureActivity(), ScreenView {

    private companion object {

        private val TAG = FeatureActivity::class.simpleName
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun showScreen(screen: Screen) {
        error("Unknown screen: $screen")
    }

}

abstract class ReplicantActivity<State> : ScreenActivity() {

    protected abstract fun renderState(viewState: State)

}
