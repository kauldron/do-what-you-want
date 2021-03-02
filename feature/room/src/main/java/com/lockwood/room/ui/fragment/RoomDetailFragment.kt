package com.lockwood.room.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lockwood.automata.android.newFragment
import com.lockwood.dwyw.core.ui.BaseFragment
import com.lockwood.room.R

// TODO: Fill RoomDetailFragment
class RoomDetailFragment : BaseFragment() {

    // TODO: Add args delegate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_room, container, false)

    companion object {

        private const val ROOM_ID = "room_id"

        @JvmStatic
        fun newInstance(id: Int): RoomDetailFragment = newFragment {
            putInt(ROOM_ID, id)
        }

    }
}