package com.lockwood.automata.file

import java.io.FileOutputStream

fun FileOutputStream.writeData(
		base64: String,
) {
	val fileByteArray = base64.decodeBase64()
	write(fileByteArray)
	close()
}
