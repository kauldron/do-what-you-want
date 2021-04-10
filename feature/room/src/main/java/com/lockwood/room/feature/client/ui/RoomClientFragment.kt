package com.lockwood.room.feature.client.ui

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat.setImageTintList
import androidx.core.widget.ImageViewCompat.setImageTintMode
import androidx.fragment.app.viewModels
import com.lockwood.automata.android.newFragment
import com.lockwood.automata.android.startForegroundService
import com.lockwood.automata.android.stopService
import com.lockwood.dwyw.core.ui.BaseFragment
import com.lockwood.dwyw.ui.core.Colors
import com.lockwood.replicant.event.Event
import com.lockwood.replicant.event.observeEvents
import com.lockwood.replicant.ext.observeState
import com.lockwood.replicant.view.ext.requireProgressView
import com.lockwood.replicant.view.ext.setDebouncingOnClickListener
import com.lockwood.room.R
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.feature.client.event.StartClientServiceEvent
import com.lockwood.room.feature.client.event.StopClientServiceEvent
import com.lockwood.room.feature.client.service.ClientForegroundService
import com.lockwood.room.model.Room

internal class RoomClientFragment : BaseFragment<RoomClientViewState>() {

	private val viewModel by viewModels<RoomClientViewModel> {
		getFeature<RoomsFeature>().viewModelsFactory
	}

	override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?,
	): View = inflater.inflate(R.layout.fragment_client_room, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		requireProgressView().hideProgress()

		with(viewModel) {
			observeState(liveState, ::renderState)
			observeEvents(eventsQueue, ::onEvent)

			onConnected()
		}
	}

	override fun onEvent(event: Event) {
		when (event) {
			is StartClientServiceEvent -> requireContext().startForegroundService<ClientForegroundService>()
			is StopClientServiceEvent -> requireContext().stopService<ClientForegroundService>()
			else -> super.onEvent(event)
		}
	}

	override fun renderState(viewState: RoomClientViewState) = with(viewState) {
		if (isEnabled) {
			renderImage(isConnected)
			renderButtonPlaying(isConnected, room)
			renderCaptionPlaying(isConnected, room)
		} else {
			renderDisabled()
		}
	}

	private fun renderDisabled() {
		requireButtonView().apply {
			backgroundTintMode = PorterDuff.Mode.SRC_IN
			backgroundTintList = ColorStateList.valueOf(Colors.GRAY)
			setOnClickListener {}
		}

		requireImageView().apply {
			setImageTintMode(this, PorterDuff.Mode.SRC_IN)
			setImageTintList(this, ColorStateList.valueOf(Colors.BLACK))
			setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_headset))
		}
	}

	private fun renderButtonPlaying(isConnected: Boolean, room: Room) {
		requireButtonView().apply {
			if (isConnected) {
				text = "Disconnect"
				backgroundTintList = ColorStateList.valueOf(Colors.RED)
				setTextColor(Colors.WHITE)
				setDebouncingOnClickListener { viewModel.onDisconnect() }
			} else {
				text = "Connect to ${room.name}"
				backgroundTintList = ColorStateList.valueOf(Colors.GRAY)
				setTextColor(Colors.PURPLE)
				setDebouncingOnClickListener { viewModel.requestConnection() }
			}
		}
	}

	private fun renderCaptionPlaying(isConnected: Boolean, room: Room) {
		requireTextView().apply {
			text = if (isConnected) {
				"You are connected to\n${room.name}"
			} else {
				"You are\nNot connected to anyone"
			}
		}
	}

	private fun renderImage(isConnected: Boolean) {
		requireImageView().apply {
			val imageTintList = if (isConnected) {
				ColorStateList.valueOf(Colors.PURPLE_VARIANT)
			} else {
				ColorStateList.valueOf(Colors.GRAY)
			}
			setImageTintList(this, imageTintList)
			setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_headset))
		}
	}

	private fun requireButtonView(): Button {
		return requireView().findViewById(R.id.start_discovery_button)
	}

	private fun requireTextView(): TextView {
		return requireView().findViewById(R.id.stub_title_text_view)
	}

	private fun requireImageView(): ImageView {
		return requireView().findViewById(R.id.stub_image_view)
	}

	companion object {

		@JvmStatic
		fun newInstance(): RoomClientFragment = newFragment()

	}
}