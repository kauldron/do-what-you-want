package com.lockwood.room.navigation.launcher.license

import android.content.Context
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.lockwood.automata.android.launchActivity

internal class ThirdPartyLicenseLauncher : IThirdPartyLicenseLauncher {

	override fun launch(context: Context) {
		context.launchActivity<OssLicensesMenuActivity>()
	}

}
