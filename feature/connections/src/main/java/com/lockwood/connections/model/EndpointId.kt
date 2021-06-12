package com.lockwood.connections.model

@JvmInline
value class EndpointId(
    @JvmField
    private val value: String
) {

    override fun toString(): String = value
}