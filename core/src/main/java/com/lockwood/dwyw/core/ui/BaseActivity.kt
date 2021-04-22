package com.lockwood.dwyw.core.ui

import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lockwood.automata.android.showFragment
import com.lockwood.replicant.base.FeatureActivity

abstract class BaseActivity : FeatureActivity() {

	protected inline fun <reified T : Fragment> showFragment(
			@IdRes id: Int,
			fragment: Fragment,
	) {
		supportFragmentManager.showFragment<T>(id, fragment) {
			setCustomAnimations(
					android.R.anim.fade_in,
					android.R.anim.fade_out,
					android.R.anim.fade_in,
					android.R.anim.fade_out,
			)
		}
	}

	protected inline fun showDialog(
			onBuild: MaterialAlertDialogBuilder.() -> MaterialAlertDialogBuilder
	) {
		MaterialAlertDialogBuilder(this).setCancelable(false).apply {
			onBuild(this)
		}.show()
	}

	protected fun showToast(string: String) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
	}
}
