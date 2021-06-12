package com.lockwood.replicant.feature.ext

import com.lockwood.replicant.feature.PermissionsFeature

fun <T : PermissionsFeature> mergePermissions(vararg features: T): Array<String> {
    return features.flatMap { it.requiredPermissions.asIterable() }.toTypedArray()
}