package com.lockwood.replicant.cache

interface CacheManager : CacheStore {

    fun invalidate(key: String)

    fun invalidateAll()

}