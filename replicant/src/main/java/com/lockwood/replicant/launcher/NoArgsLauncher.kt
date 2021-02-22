package com.lockwood.replicant.launcher

import android.content.Intent

interface NoArgsLauncher {

	fun launch()

	fun buildIntent(): Intent
}