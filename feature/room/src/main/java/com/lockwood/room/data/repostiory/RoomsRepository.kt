package com.lockwood.room.data.repostiory

import androidx.annotation.WorkerThread
import com.lockwood.connections.INearbyConnectionsManager
import com.lockwood.replicant.executor.ExecutorProvider
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.util.concurrent.Executor

internal class RoomsRepository(
		private val nearbyConnectionsManager: INearbyConnectionsManager,
		private val executorProvider: ExecutorProvider
) :
		IRoomsRepository,
		INearbyConnectionsManager by nearbyConnectionsManager {

	private val outputExecutor: Executor by lazy {
		executorProvider.io()
	}

	private val inputExecutor: Executor by lazy {
		executorProvider.io()
	}

	private val pipedOutputStream = PipedOutputStream()

	private val pipedInputStream = PipedInputStream().apply {
		connect(pipedOutputStream)
	}

	override fun sendPayload(byteArray: ByteArray) {
		writeData(byteArray)
		sendData()
	}

	@WorkerThread
	private fun writeData(byteArray: ByteArray) = inputExecutor.execute {
		pipedOutputStream.write(byteArray)
	}

	@WorkerThread
	private fun sendData() = outputExecutor.execute {
		sendPayload(pipedInputStream)
	}

}
