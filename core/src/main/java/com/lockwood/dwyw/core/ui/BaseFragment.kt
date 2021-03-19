package com.lockwood.dwyw.core.ui

import com.lockwood.replicant.base.ReplicantFragment
import com.lockwood.replicant.state.ViewState
import com.lockwood.replicant.view.ext.requireProgressView

abstract class BaseFragment<VS : ViewState> : ReplicantFragment<VS>() {

	protected fun renderLoading(isLoading: Boolean) {
		requireProgressView().updateProgressVisibility(isLoading)
	}

}