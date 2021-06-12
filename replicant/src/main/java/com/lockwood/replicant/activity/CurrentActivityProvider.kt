package com.lockwood.replicant.activity

import android.app.Activity
import com.lockwood.replicant.delegate.weakReference

class CurrentActivityProvider : ICurrentActivityProvider {

    override var currentActivity: Activity? by weakReference { null }
}
