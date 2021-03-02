package com.lockwood.dwyw.core.ui

import androidx.fragment.app.Fragment
import com.lockwood.automata.android.replaceFragment
import com.lockwood.automata.android.replaceFragmentWithBackStack
import com.lockwood.automata.res.IdRes
import com.lockwood.replicant.base.ReplicantActivity

abstract class BaseActivity : ReplicantActivity() {

    protected fun showFragment(id: IdRes, fragment: Fragment) = with(supportFragmentManager) {
        if (findFragmentById(id.value) == null) {
            replaceFragment(id, fragment)
        } else {
            replaceFragmentWithBackStack(id, fragment)
        }
    }

}