package com.lockwood.automata.file

import java.io.File

fun File.deleteDirectory(): Boolean {
    return when {
        isDirectory -> {
            val childrenFiles = list()
            if (childrenFiles.isNullOrEmpty()) {
                return delete()
            }

            // delete inner files and directories first
            childrenFiles.forEach {
                val success = File(this, it).deleteDirectory()
                if (!success) {
                    return false
                }
            }

            return delete()
        }
        isFile -> {
            delete()
        }
        else -> {
            false
        }
    }
}
