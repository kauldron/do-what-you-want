@file:Suppress("NOTHING_TO_INLINE")

package com.lockwood.automata.core

import java.io.IOException

inline fun ioError(message: Any): Nothing = throw IOException(message.toString())