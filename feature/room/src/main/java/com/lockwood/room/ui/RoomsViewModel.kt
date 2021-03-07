package com.lockwood.room.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lockwood.replicant.ext.delegate
import com.lockwood.room.data.IRoomRepository
import java.util.concurrent.TimeUnit

class RoomsViewModel(
    private val repository: IRoomRepository
) : ViewModel() {

    // TODO: Add delegate class to store MutableLiveData and ViewState
    val liveState: MutableLiveData<RoomsViewState> = MutableLiveData(RoomsViewState.initialState)

    private var state: RoomsViewState by liveState.delegate()

    fun fetchRooms() {
        val roomsList = repository.fetchRooms().toTypedArray()
        TimeUnit.SECONDS.sleep(5)
        state = state.copy(rooms = roomsList, isLoading = false)
    }

}