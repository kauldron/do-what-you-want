package com.lockwood.dwyw.core

import android.os.Handler
import android.os.Looper
import com.lockwood.replicant.base.ReplicantViewModel
import com.lockwood.replicant.state.ViewState

abstract class BaseViewModel<VS : ViewState>(initState: VS) : ReplicantViewModel<VS>(initState) {

  private companion object {

    private const val DEFAULT_LOADING_DELAY = 500L
  }

  private val mainHandler: Handler = Handler((Looper.getMainLooper()))

  protected fun postDelay(action: Runnable, delay: Long) {
    mainHandler.postDelayed(action, delay)
  }

  protected fun postDelay(action: Runnable) {
    postDelay(action, DEFAULT_LOADING_DELAY)
  }

  protected fun runOnUiThread(action: Runnable) {
    mainHandler.post(action)
  }
}
