package com.lockwood.replicant.view

interface ProgressView {

    fun showProgress()

    fun hideProgress()

    fun updateProgressVisibility(isVisible: Boolean) {
        if (isVisible) {
            showProgress()
        } else {
            hideProgress()
        }
    }
}
