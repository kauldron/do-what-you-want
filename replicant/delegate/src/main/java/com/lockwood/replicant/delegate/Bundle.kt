@file:Suppress("UNCHECKED_CAST")

package com.lockwood.replicant.delegate

import android.app.Fragment
import android.os.Bundle
import android.os.Parcelable
import com.lockwood.automata.core.EMPTY
import com.lockwood.automata.core.UNDEFINED
import com.lockwood.automata.core.newInstance
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <T> Fragment.bundleArg(
    crossinline getter: Bundle.() -> T,
    crossinline setter: Bundle.(T) -> Unit,
): ReadWriteProperty<Any?, T> {
    return object : ReadWriteProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return arguments.getter()
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            arguments.setter(value)
        }
    }
}

inline fun <T> Fragment.intArg(
    name: String,
    crossinline default: () -> Int = { Int.UNDEFINED },
): ReadWriteProperty<Any?, T> {
    return bundleArg(
        getter = { getInt(name, default()) as T },
        setter = { value -> putInt(name, value as Int) }
    )
}

inline fun <T> Fragment.stringArgs(
    name: String,
    crossinline default: () -> String = { String.EMPTY },
): ReadWriteProperty<Any?, T> {
    return bundleArg(
        getter = { getString(name, default()) as T ?: default() as T },
        setter = { value -> putString(name, value as String) }
    )
}

inline fun <reified T : Parcelable> Fragment.parcelableArgs(
    name: String,
    crossinline default: () -> T = { newInstance() },
): ReadWriteProperty<Any?, T> {
    return bundleArg(
        getter = { getParcelable(name) ?: default() },
        setter = { value -> putParcelable(name, value) }
    )
}
