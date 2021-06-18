package com.lockwood.replicant.delegate

object Delegates {

    fun <T> fakeNotNull(): FakeNotNullVar<T> = FakeNotNullVar()

    inline fun <T> weakReference(value: () -> T?): WeakReference<T?> = WeakReference(value.invoke())
}