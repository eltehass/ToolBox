package com.example.toolbox.tools

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex

inline fun <T> MutableStateFlow<T>.update(job: Job?, function: (T) -> T) {
    job?.let {
        if (it.isActive) {
            update { function(value) }
        }
    }
}

// Ignores all emits until delay time is passed after latest emit
fun <T> Flow<T>.throttled(delayMillis: Long = 1000): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { value ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmissionTime >= delayMillis) {
            emit(value)
            lastEmissionTime = currentTime
        }
    }
}

// Ignores all emits until locker is unlocked on the collector side
fun <T> Flow<T>.ignoreUntilUnlocked(): Flow<Pair<Mutex, T>> = flow {
    val localLock = Mutex()
    collect { value ->
        if (!localLock.isLocked) {
            localLock.tryLock()
            emit(localLock to value)
        }
    }
}
