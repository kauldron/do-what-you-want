package com.lockwood.replicant.view.listener

// TODO: Replace any with Item
@FunctionalInterface
interface ItemClickListener<T : Any> {

    fun onClick(item: T)
}