package com.lockwood.replicant.mapper

interface OneWayMapper<E, M> {

	fun mapFrom(type: E): M
}
