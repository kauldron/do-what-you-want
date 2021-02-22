package com.lockwood.replicant.event

open class MessageEvent(val message: String) : Event
class ErrorMessageEvent(message: String) : MessageEvent(message)