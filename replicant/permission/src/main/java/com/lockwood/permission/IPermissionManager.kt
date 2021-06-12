package com.lockwood.permission

import android.app.Activity

interface IPermissionManager {

    fun requestPermissions(activity: Activity, vararg permissions: String)

    fun isPermissionsGranted(activity: Activity, requestCode: Int, grantResults: IntArray): Boolean

    fun hasPermissions(vararg permissions: String): Boolean

}