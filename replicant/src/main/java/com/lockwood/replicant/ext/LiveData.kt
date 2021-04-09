package com.lockwood.replicant.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T : Any> MutableLiveData<T>.delegate(): ReadWriteProperty<Any, T> {
	return object : ReadWriteProperty<Any, T> {

		override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
			this@delegate.value = value
		}

		override fun getValue(thisRef: Any, property: KProperty<*>): T {
			return checkNotNull(value)
		}
	}
}

inline fun <T, LD : LiveData<T>> Fragment.observeState(
		liveData: LD,
		crossinline block: (T) -> Unit,
) = liveData.observe(viewLifecycleOwner, { block(it) })

inline fun <T, LD : LiveData<T>> FragmentActivity.observeState(
		liveData: LD,
		crossinline block: (T) -> Unit,
) = liveData.observe(this, { block(it) })
