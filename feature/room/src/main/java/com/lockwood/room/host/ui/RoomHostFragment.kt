package com.lockwood.room.host.ui

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.lockwood.automata.android.newFragment
import com.lockwood.automata.android.startForegroundService
import com.lockwood.automata.android.stopService
import com.lockwood.dwyw.core.ui.BaseFragment
import com.lockwood.dwyw.ui.core.Colors
import com.lockwood.replicant.event.Event
import com.lockwood.replicant.event.observeEvents
import com.lockwood.replicant.ext.observeState
import com.lockwood.room.R
import com.lockwood.room.event.StartHostServiceEvent
import com.lockwood.room.event.StopHostServiceEvent
import com.lockwood.room.feature.RoomsFeature
import com.lockwood.room.service.HostForegroundService

internal class RoomHostFragment : BaseFragment<RoomHostViewState>() {

  private val viewModel by viewModels<RoomHostViewModel> {
    getFeature<RoomsFeature>().viewModelsFactory
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View = inflater.inflate(R.layout.fragment_host_room, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    with(viewModel) {
      observeState(liveState, ::renderState)
      observeEvents(eventsQueue, ::onEvent)

      startBroadcasting()
    }
  }

  override fun onEvent(event: Event) {
    when (event) {
      StartHostServiceEvent -> requireContext().startForegroundService<HostForegroundService>()
      StopHostServiceEvent -> requireContext().stopService<HostForegroundService>()
      else -> super.onEvent(event)
    }
  }

  override fun renderState(viewState: RoomHostViewState) {
    renderButtonEnabled(viewState.isEnabled)
    if (viewState.isEnabled) {
      renderButtonSharing(viewState.isSharing)
    }
  }

  private fun renderButtonEnabled(isEnabled: Boolean) {
    if (!isEnabled) {
      requireButtonView().apply {
        backgroundTintMode = PorterDuff.Mode.SRC_IN
        backgroundTintList = ColorStateList.valueOf(Colors.GRAY)
        setOnClickListener {}
      }

      requireImageView().apply { imageTintList = ColorStateList.valueOf(Colors.BLACK) }
    }
  }

  private fun renderButtonSharing(isSharing: Boolean) {
    requireButtonView().apply {
      if (isSharing) {
        text = "Stop"
        backgroundTintList = ColorStateList.valueOf(Colors.RED)
        setTextColor(Colors.WHITE)
        setOnClickListener { viewModel.stopBroadcasting() }
      } else {
        text = "Start"
        backgroundTintList = ColorStateList.valueOf(Colors.GRAY)
        setTextColor(Colors.PURPLE)
        setOnClickListener { viewModel.startBroadcasting() }
      }
    }

    requireTextView().apply {
      text =
        if (isSharing) {
          "You are\nBroadcasting now :)"
        } else {
          "You are not\nBroadcasting now :("
        }
    }

    requireImageView().apply {
      imageTintList =
        if (isSharing) {
          ColorStateList.valueOf(Colors.PURPLE_VARIANT)
        } else {
          ColorStateList.valueOf(Colors.GRAY)
        }
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

    @JvmStatic fun newInstance(): RoomHostFragment = newFragment()
  }
}
