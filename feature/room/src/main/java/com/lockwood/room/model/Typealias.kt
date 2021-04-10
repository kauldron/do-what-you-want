package com.lockwood.room.model

import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract

internal typealias UnitResultLauncher = ActivityResultContract<Unit, Boolean>
internal typealias PermissionsResultLauncher = ActivityResultLauncher<Array<String>>
internal typealias PermissionsResultCallback = ActivityResultCallback<Map<String, Boolean>>
