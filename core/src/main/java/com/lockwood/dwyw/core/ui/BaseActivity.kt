package com.lockwood.dwyw.core.ui

import android.app.AlertDialog
import android.app.Fragment
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import com.lockwood.automata.android.asApplication
import com.lockwood.automata.android.showFragment
import com.lockwood.automata.core.notSafeLazy
import com.lockwood.permission.IPermissionManager
import com.lockwood.permission.PermissionManager
import com.lockwood.permission.callback.RequestPermissionsResultCallback
import com.lockwood.replicant.base.ScreenActivity
import com.lockwood.replicant.view.MessageView

abstract class BaseActivity : ScreenActivity(),
    MessageView, RequestPermissionsResultCallback {

    private val permissionsManager: IPermissionManager by notSafeLazy {
        PermissionManager(applicationContext.asApplication())
    }

    protected inline fun <reified T : Fragment> showFragment(
        @IdRes id: Int,
        fragment: Fragment,
    ) {
        fragmentManager.showFragment<T>(id, fragment) {

            setCustomAnimations(
                android.R.animator.fade_in,
                android.R.animator.fade_out,
                android.R.animator.fade_in,
                android.R.animator.fade_out,
            )
        }
    }

    @CallSuper
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        val isGranted = permissionsManager.isPermissionsGranted(this, requestCode, grantResults)
        val result = permissions.zip(grantResults.toList()).toMap()

        onRequestPermissionsResult(grantResults = result)
        onRequestPermissionsResult(isAllPermissionsGranted = isGranted)
    }

    protected fun requestPermissions(vararg permissions: String) {
        permissionsManager.requestPermissions(this, *permissions)
    }

    protected fun hasPermissions(vararg permissions: String): Boolean {
        return permissionsManager.hasPermissions(*permissions)
    }

    protected inline fun showDialog(
        onBuild: AlertDialog.Builder.() -> AlertDialog.Builder,
    ) {
        AlertDialog.Builder(this).setCancelable(false).apply {
            onBuild(this)
        }.show()
    }

    override fun showMessage(message: String) {
        showToast(message)
    }

    override fun showError(message: String) {
        showToast(message)
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

}
