package com.lockwood.permission.callback

interface RequestPermissionsResultCallback {

    fun onRequestPermissionsResult(permissions: Array<out String>, grantResults: IntArray) = Unit

    fun onRequestPermissionsResult(isAllPermissionsGranted: Boolean) = Unit

}