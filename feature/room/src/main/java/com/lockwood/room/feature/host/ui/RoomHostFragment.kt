package com.lockwood.room.feature.host.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.widget.ImageViewCompat.setImageTintList
import androidx.core.widget.ImageViewCompat.setImageTintMode
import androidx.fragment.app.viewModels
import com.lockwood.automata.android.newFragment
import com.lockwood.automata.android.startForegroundService
import com.lockwood.automata.android.stopService
import com.lockwood.dwyw.core.ui.BaseFragment
import com.lockwood.dwyw.ui.core.Colors
import com.lockwood.recorder.IAudioRecorder
import com.lockwood.recorder.feature.RecorderFeature
import com.lockwood.replicant.context.ApplicationContextProvider
import com.lockwood.replicant.event.Event
import com.lockwood.replicant.event.observeEvents
import com.lockwood.replicant.ext.observeState
import com.lockwood.replicant.view.ext.requireActivityType
import com.lockwood.replicant.view.ext.requireMessageView
import com.lockwood.replicant.view.ext.requireProgressView
import com.lockwood.replicant.view.ext.setDebouncingOnClickListener
import com.lockwood.room.R
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.feature.discover.event.ShowAcceptConnectionEvent
import com.lockwood.room.feature.host.event.RequestCaptureEvent
import com.lockwood.room.feature.host.event.StartHostServiceEvent
import com.lockwood.room.feature.host.event.StopHostServiceEvent
import com.lockwood.room.feature.host.service.HostForegroundService
import com.lockwood.room.model.Room

internal class RoomHostFragment : BaseFragment<RoomHostViewState>(), IHostView {

	private val viewModel by viewModels<RoomHostViewModel> {
		getFeature<RoomsFeature>().viewModelsFactory
	}

	private val audioRecorder: IAudioRecorder
		get() = getFeature<RecorderFeature>().audioRecorder

	private val appContext: Context
		get() {
			val contextProvider = (requireContext().applicationContext as ApplicationContextProvider)
			return contextProvider.applicationContext.value
		}

	override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?,
	): View = inflater.inflate(R.layout.fragment_host_room, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		requireProgressView().hideProgress()

		with(viewModel) {
			observeState(liveState, ::renderState)
			observeEvents(eventsQueue, ::onEvent)

			startBroadcasting()
		}
	}

	override fun onEvent(event: Event) {
		when (event) {
			is RequestCaptureEvent -> requestCapture()
			is StartHostServiceEvent -> appContext.startForegroundService<HostForegroundService>()
			is StopHostServiceEvent -> appContext.stopService<HostForegroundService>()
			is ShowAcceptConnectionEvent -> showConnectionDialog(event.room)
			else -> super.onEvent(event)
		}
	}

	override fun renderState(viewState: RoomHostViewState) = with(viewState) {
		if (isEnabled) {
			renderActionButton(isSharing)
			renderImage(isSharing)
			renderCaption(isSharing)
		} else {
			renderDisabled()
		}
	}

	override fun requestCapture() {
		requireActivityType<IHostView>().requestCapture()
	}

	override fun onCaptureGranted(isGranted: Boolean) = if (isGranted) {
		viewModel.onStartBroadcasting()
		audioRecorder.start()
	} else {
		viewModel.stopBroadcasting()
		requireMessageView().showError("Not all permissions granted ( ｰ̀εｰ́ )")
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
			setImageDrawable(getDrawable(requireContext(), com.lockwood.dwyw.ui.core.R.drawable.ic_broadcast))
		}
	}

	private fun renderActionButton(isSharing: Boolean) {
		requireButtonView().apply {
			if (isSharing) {
				isEnabled = true
				text = "Stop"
				backgroundTintList = ColorStateList.valueOf(Colors.RED)
				setTextColor(Colors.WHITE)
				setDebouncingOnClickListener { viewModel.stopBroadcasting() }
			} else {
				// temporary workaround to prevent crash because of uninitialized AudioRecord
				isEnabled = false
				text = "Start"
				backgroundTintList = ColorStateList.valueOf(Colors.GRAY)
				setTextColor(Colors.PURPLE)
				setDebouncingOnClickListener { viewModel.startBroadcasting() }
			}
		}
	}

	private fun renderCaption(isSharing: Boolean) {
		requireTextView().apply {
			text = if (isSharing) {
				"You are\nBroadcasting now"
			} else {
				"You are not\nBroadcasting now"
			}
		}
	}

	private fun renderImage(isSharing: Boolean) {
		requireImageView().apply {
			val imageTintList = if (isSharing) {
				ColorStateList.valueOf(Colors.PURPLE_VARIANT)
			} else {
				ColorStateList.valueOf(Colors.GRAY)
			}
			setImageTintList(this, imageTintList)
			setImageDrawable(getDrawable(requireContext(), com.lockwood.dwyw.ui.core.R.drawable.ic_broadcast))
		}
	}

	private fun showConnectionDialog(room: Room) = showDialog {
		setTitle("Attention")
		setMessage("Accept connection with ${room.name}?")
		setNegativeButton("No") { dialog, _ ->
			viewModel.rejectConnection(room)
			dialog.dismiss()
		}
		setPositiveButton("Yes") { _, _ ->
			viewModel.acceptConnection(room)
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
		fun newInstance(): RoomHostFragment = newFragment()
	}

}
