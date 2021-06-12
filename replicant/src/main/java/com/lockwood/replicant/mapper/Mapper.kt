package com.lockwood.replicant.mapper

interface Mapper<E, M> : OneWayMapper<E, M> {

    fun mapTo(type: M): E
}
