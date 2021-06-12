package com.lockwood.replicant.inflater

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class BaseAsyncLayoutInflater(
    context: Context,
) : LayoutInflater(context) {

    private companion object {

        private const val TAG = "BaseAsyncLayoutInflater"

        @JvmStatic
        private val S_CLASS_PREFIX_LIST = arrayOf(
            "android.widget.",
            "android.webkit.",
            "android.app."
        )
    }

    private val mainHandler: Handler = Handler(Looper.getMainLooper())

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    override fun onCreateView(name: String, attrs: AttributeSet): View {
        for (prefix in S_CLASS_PREFIX_LIST) {
            try {
                val view = createView(name, prefix, attrs)
                if (view != null) {
                    return view
                }
            } catch (e: ClassNotFoundException) {
                Log.e(TAG, e.message.toString())
            }
        }

        return super.onCreateView(name, attrs)
    }

    abstract fun inflate(
        resId: Int,
        viewGroup: ViewGroup? = null,
        callback: (View, Int, ViewGroup?) -> Unit,
    )

    protected fun runOnUiThread(action: Runnable) {
        mainHandler.post(action)
    }

    protected fun runOnWorkerThread(action: Runnable) {
        executorService.execute(action)
    }

}