package com.lockwood.dwyw.core.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.lockwood.automata.android.replaceFragment
import com.lockwood.automata.android.replaceFragmentWithBackStack
import com.lockwood.replicant.base.ReplicantActivity

abstract class BaseActivity : ReplicantActivity() {

    protected fun showFragment(@IdRes id: Int, fragment: Fragment) = with(supportFragmentManager) {
        if (findFragmentById(id) == null) {
            replaceFragment(id, fragment)
        } else {
            replaceFragmentWithBackStack(id, fragment)
        }
    }

}