package com.lockwood.dwyw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lockwood.automata.android.launchActivity
import com.lockwood.room.RoomActivity

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		launchActivity<RoomActivity>()
	}

}
