package com.lockwood.replicant.receiver

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ReceiverManagerDelegate<T : ReceiverManager>
@PublishedApi
internal constructor(
		activity: FragmentActivity,
		initializer: () -> T,
) : ReadOnlyProperty<Any?, T> {

	private companion object {

		private const val TAG = "ReceiverManagerDelegate"
	}

	private var initializer: (() -> T)? = initializer

	private lateinit var receiverManager: T

	init {
		activity.lifecycle.addObserver(
				LifecycleEventObserver { _, event ->
					when (event) {
						Lifecycle.Event.ON_CREATE -> {
							initReceiverManager()
							Log.d(TAG, "createReceiver: ${receiverManager::class.java}")
						}
						Lifecycle.Event.ON_RESUME -> {
							Log.d(TAG, "registerReceiver: ${receiverManager::class.java}")
							receiverManager.registerReceiver(activity)
						}
						Lifecycle.Event.ON_STOP -> {
							Log.d(TAG, "unregisterReceiver: ${receiverManager::class.java}")
							receiverManager.unregisterReceiver(activity)
						}
						else -> Unit
					}
				}
		)
	}

	override fun getValue(thisRef: Any?, property: KProperty<*>): T = receiverManager

	private fun initReceiverManager() {
		if (!this::receiverManager.isInitialized) {
			receiverManager = initializer!!()
			initializer = null
		}
	}
}

fun <T : ReceiverManager> FragmentActivity.lifecycleAwareReceiver(
		init: () -> T,
): ReadOnlyProperty<Any?, T> = ReceiverManagerDelegate(this, init)
