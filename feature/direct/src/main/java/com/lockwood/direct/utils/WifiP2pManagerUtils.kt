package com.lockwood.direct.utils

import android.net.wifi.p2p.WifiP2pManager
import com.lockwood.direct.WifiP2pActionListener
import com.lockwood.direct.WifiP2pError
import com.lockwood.direct.WifiP2pError.BUSY
import com.lockwood.direct.WifiP2pError.ERROR
import com.lockwood.direct.WifiP2pError.NO_SERVICE_REQUESTS
import com.lockwood.direct.WifiP2pError.P2P_UNSUPPORTED

internal object WifiP2pManagerUtils {

	@JvmStatic
	fun wrapWithManagerListener(p2pActionListener: WifiP2pActionListener): WifiP2pManager.ActionListener {
		return object : WifiP2pManager.ActionListener {
			override fun onSuccess() {
				p2pActionListener.onSuccess()
			}

			override fun onFailure(errorCode: Int) {
				val error: WifiP2pError = mapToP2pError(errorCode)
				p2pActionListener.onFailure(error)
			}
		}
	}

	@JvmStatic
	private fun mapToP2pError(code: Int): WifiP2pError = when (code) {
		WifiP2pManager.BUSY -> BUSY
		WifiP2pManager.P2P_UNSUPPORTED -> P2P_UNSUPPORTED
		WifiP2pManager.NO_SERVICE_REQUESTS -> NO_SERVICE_REQUESTS
		else -> ERROR
	}

}