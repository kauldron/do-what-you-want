package com.lockwood.replicant.activity

import android.app.Activity
import com.lockwood.replicant.delegate.weakReference

class CurrentActivityProviderImpl : CurrentActivityProvider {

  override var currentActivity: Activity by weakReference()
}
