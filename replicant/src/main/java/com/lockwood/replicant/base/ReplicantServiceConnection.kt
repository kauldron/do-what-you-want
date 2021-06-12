package com.lockwood.replicant.base

import android.app.Service
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.annotation.CallSuper
import com.lockwood.automata.android.ApplicationContext
import com.lockwood.automata.android.newIntent

abstract class ReplicantServiceConnection : ServiceConnection {

    protected var isBound = false

    @CallSuper
    override fun onServiceConnected(className: ComponentName, service: IBinder) {
        isBound = true
    }

    @CallSuper
    override fun onServiceDisconnected(name: ComponentName?) {
        isBound = false
    }

    fun unbindService(context: ApplicationContext) = with(context.value) {
        if (isBound) {
            unbindService(this@ReplicantServiceConnection)
        }
    }

    protected inline fun <reified T : Service> bindService(
        context: ApplicationContext,
        flags: Int,
    ) = with(context.value) {
        if (!isBound) {
            val intent = newIntent<T>(this)
            bindService(intent, this@ReplicantServiceConnection, flags)
        }
    }

}