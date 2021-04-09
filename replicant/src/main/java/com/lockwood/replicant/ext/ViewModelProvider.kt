package com.lockwood.replicant.ext

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import kotlin.reflect.KClass

@MainThread
@Suppress("UNCHECKED_CAST")
inline fun <reified VM : ViewModel> ViewModelStoreOwner.lazyViewModel(
		noinline viewModelProducer: () -> VM,
): Lazy<VM> {
	val factoryPromise =
			object : ViewModelProvider.Factory {
				override fun <T : ViewModel> create(modelClass: Class<T>): T {
					return viewModelProducer() as T
				}
			}
	return ViewModelLazy(VM::class, { viewModelStore }, { factoryPromise })
}

class ViewModelLazy<VM : ViewModel>(
		private val viewModelClass: KClass<VM>,
		private val storeProducer: () -> ViewModelStore,
		private val factoryProducer: () -> ViewModelProvider.Factory,
) : Lazy<VM> {

	private var cached: VM? = null

	override val value: VM
		get() {
			val viewModel = cached
			return if (viewModel == null) {
				val factory = factoryProducer()
				val store = storeProducer()
				ViewModelProvider(store, factory).get(viewModelClass.java).also { cached = it }
			} else {
				viewModel
			}
		}

	override fun isInitialized() = cached != null
}

@MainThread
inline fun <reified VM : ViewModel> ViewModelProvider.get() = get(VM::class.java)
