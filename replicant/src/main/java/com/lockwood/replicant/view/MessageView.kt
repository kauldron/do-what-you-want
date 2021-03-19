package com.lockwood.replicant.view

interface MessageView {

	fun showMessage(message: String)

	fun showError(message: String)
}