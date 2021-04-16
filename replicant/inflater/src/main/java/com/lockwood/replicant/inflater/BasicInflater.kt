package com.lockwood.replicant.inflater

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View

internal class BasicInflater(
		context: Context
) : LayoutInflater(context) {

	private companion object {

		private const val TAG = "BasicInflater"

		@JvmStatic
		private val S_CLASS_PREFIX_LIST = arrayOf(
				"android.widget.",
				"android.webkit.",
				"android.app."
		)
	}

	override fun cloneInContext(newContext: Context): LayoutInflater {
		return BasicInflater(newContext)
	}

	override fun onCreateView(name: String, attrs: AttributeSet): View {
		for (prefix in S_CLASS_PREFIX_LIST) {
			try {
				val view = createView(name, prefix, attrs)
				if (view != null) {
					return view
				}
			} catch (e: ClassNotFoundException) {
				Log.e(TAG, e.message.toString())
			}
		}

		return super.onCreateView(name, attrs)
	}

}