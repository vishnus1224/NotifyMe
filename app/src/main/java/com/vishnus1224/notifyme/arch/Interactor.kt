package com.vishnus1224.notifyme.arch

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class Interactor<ACTION, RESULT> {

    private val resultChannel = Channel<RESULT>()

    private val mutex = Mutex()

    abstract suspend fun process(action: ACTION)
    abstract fun destroy()

    fun results(): Flow<RESULT> = resultChannel.consumeAsFlow()

    protected suspend fun sendResults(vararg results: RESULT) {
        // Lock the iteration of results with a mutex so that results are delivered in order.
        mutex.withLock {
            results.forEach {
                resultChannel.send(it)
            }
        }
    }
}