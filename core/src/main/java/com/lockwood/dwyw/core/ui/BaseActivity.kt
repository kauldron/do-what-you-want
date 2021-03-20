package com.lockwood.dwyw.core.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.lockwood.automata.android.isBackStackNotEmpty
import com.lockwood.automata.android.showFragment
import com.lockwood.automata.android.showFragmentFromBackStack
import com.lockwood.replicant.base.FeatureActivity
import com.lockwood.replicant.base.ReplicantActivity
import com.lockwood.replicant.state.ViewState
import com.lockwood.replicant.view.ext.requireProgressView

abstract class BaseActivity : FeatureActivity()

abstract class BaseStateActivity<VS : ViewState> : ReplicantActivity<VS>() {

  override fun onBackPressed() =
    with(supportFragmentManager) {
      if (isBackStackNotEmpty) {
        popBackStack()
        return@with
      }
      super.onBackPressed()
    }

  protected fun renderLoading(isLoading: Boolean) {
    requireProgressView().updateProgressVisibility(isLoading)
  }

  protected fun showFragment(@IdRes id: Int, fragment: Fragment, fromBackStack: Boolean = false) {
    if (fromBackStack) {
      supportFragmentManager.showFragment(id, fragment)
    } else {
      supportFragmentManager.showFragmentFromBackStack(id, fragment)
    }
  }
}
