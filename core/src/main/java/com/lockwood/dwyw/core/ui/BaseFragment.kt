package com.lockwood.dwyw.core.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lockwood.replicant.base.ReplicantFragment
import com.lockwood.replicant.delegate.FakeNotNullVar
import com.lockwood.replicant.delegate.fakeNotNull
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
        onBuild: MaterialAlertDialogBuilder.() -> MaterialAlertDialogBuilder,
    ) {
        MaterialAlertDialogBuilder(context).setCancelable(false).apply {
            onBuild(this)
        }.show()
    }

    private fun releaseStore() {
        // TODO: Test this

        if (store is Releasable) {
            (store as Releasable).release()
        }

        (store as FakeNotNullVar<*>).resetToNull()
    }

}
