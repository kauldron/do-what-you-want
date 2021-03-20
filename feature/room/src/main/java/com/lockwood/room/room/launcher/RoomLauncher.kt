package com.lockwood.room.room.launcher

import android.content.Context
import android.os.Bundle
import com.lockwood.automata.android.launchActivity
import com.lockwood.replicant.launcher.Launcher
import com.lockwood.room.RoomDirectActivity

// TODO: Use this launcher with deeplink handler
// TODO: Add QR-code generator to show in room for instant access to room
class RoomLauncher : Launcher<RoomArgs> {

	override fun launch(context: Context, launchArgs: RoomArgs) {
		val argsBundle = Bundle().apply {
			putInt(RoomArgs.ROOM_ID, launchArgs.roomId)
		}
		context.launchActivity<RoomDirectActivity>(argsBundle)
	}

}