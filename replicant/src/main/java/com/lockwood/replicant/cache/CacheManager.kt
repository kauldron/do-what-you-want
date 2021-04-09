package com.lockwood.replicant.cache

interface CacheManager {

	fun <T> get(key: String, default: T): T

	fun <T> put(key: String, value: T)

}