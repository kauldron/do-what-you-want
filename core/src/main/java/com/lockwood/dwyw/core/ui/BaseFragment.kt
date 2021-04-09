package com.lockwood.dwyw.core.ui

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lockwood.replicant.base.ReplicantFragment
import com.lockwood.replicant.state.ViewState

abstract class BaseFragment<VS : ViewState> : ReplicantFragment<VS>() {

	protected inline fun showDialog(
			onBuild: MaterialAlertDialogBuilder.() -> MaterialAlertDialogBuilder
	) {
		MaterialAlertDialogBuilder(requireContext()).setCancelable(false).apply {
			onBuild(this)
		}.show()
	}

}
