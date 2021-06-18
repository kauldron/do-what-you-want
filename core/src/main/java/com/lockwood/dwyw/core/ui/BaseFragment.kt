package com.lockwood.dwyw.core.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import com.lockwood.replicant.base.ReplicantFragment
import com.lockwood.replicant.delegate.fakeNotNull
import com.lockwood.replicant.delegate.resetFakeNotNullVar
import com.lockwood.replicant.feature.Releasable

abstract class BaseFragment<Store : Any> : ReplicantFragment() {

    protected var store: Store by fakeNotNull()
        private set

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        store = initStore(savedInstanceState)
    }

    @CallSuper
    override fun onDestroyView() {
        releaseStore()
        super.onDestroyView()
    }

    protected abstract fun initStore(savedInstanceState: Bundle?): Store

    protected inline fun showDialog(
        onBuild: AlertDialog.Builder.() -> AlertDialog.Builder,
    ) {
        AlertDialog.Builder(context).setCancelable(false).apply {
            onBuild(this)
        }.show()
    }

    private fun releaseStore() {
        if (store is Releasable) {
            (store as Releasable).release()
        }

        resetFakeNotNullVar()
    }

}
