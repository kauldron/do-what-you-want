package com.lockwood.permission

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Process
import android.util.Log
import com.lockwood.automata.android.ApplicationContext

class PermissionManager(
    private val applicationContext: ApplicationContext
) : IPermissionManager {

    override fun requestPermissions(activity: Activity, vararg permissions: String) {
        Log.d("PermissionManager", "requestPermissions[${activity.hashCode()}]: $permissions")
        activity.requestPermissions(permissions, activity.hashCode())
    }

    override fun isPermissionsGranted(
        activity: Activity,
        requestCode: Int,
        grantResults: IntArray
    ): Boolean {
        val isGranted = if (requestCode == activity.hashCode()) {
            !grantResults.contains(PackageManager.PERMISSION_DENIED)
        } else {
            true
        }

        Log.d("PermissionManager", "isPermissionsGranted: $isGranted")
        return isGranted
    }

    override fun hasPermissions(vararg permissions: String): Boolean {
        val context = applicationContext.value
        val pid = Process.myPid()
        val uid = Process.myUid()

        val grantResult = permissions.map { context.checkPermission(it, pid, uid) }

        return !grantResult.contains(PackageManager.PERMISSION_DENIED)
    }


}