package com.lockwood.dwyw.core.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.lockwood.automata.android.*
import com.lockwood.automata.core.ZERO
import com.lockwood.replicant.base.FeatureActivity

abstract class BaseActivity : FeatureActivity() {

  override fun onBackPressed() {
    with(supportFragmentManager) {
      if (backStackEntryCount > Int.ZERO) {
        popBackStack()
        return@with
      }
    }

    finish()
  }

  protected fun clearBackStack() {
    for (fragment in supportFragmentManager.fragments) {
      supportFragmentManager.transact { remove(fragment) }
    }
  }

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

  protected fun showFragmentViaBackStack(@IdRes id: Int, fragment: Fragment) {
    supportFragmentManager.showFragmentViaBackStack(id, fragment) {
      setCustomAnimations(
        android.R.anim.fade_in,
        android.R.anim.fade_out,
        android.R.anim.fade_in,
        android.R.anim.fade_out,
      )
    }
  }
}
