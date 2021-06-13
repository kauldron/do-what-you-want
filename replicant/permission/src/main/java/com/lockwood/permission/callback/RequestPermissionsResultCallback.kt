package com.lockwood.permission.callback

interface RequestPermissionsResultCallback {

    fun onRequestPermissionsResult(grantResults: Map<String, Int>) = Unit

    fun onRequestPermissionsResult(isAllPermissionsGranted: Boolean) = Unit

}