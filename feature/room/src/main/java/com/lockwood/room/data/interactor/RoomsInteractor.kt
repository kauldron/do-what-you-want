package com.lockwood.room.data.interactor

import com.lockwood.room.data.repostiory.IRoomsRepository

internal class RoomsInteractor(
  private val repository: IRoomsRepository,
) : IRoomsInteractor, IRoomsRepository by repository
