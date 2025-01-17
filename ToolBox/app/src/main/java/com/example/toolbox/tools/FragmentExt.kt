package com.example.toolbox.tools

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Fragment.collectWithLifecycle(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    collector: FlowCollector<T>,
) {
    doOnLifecycle(state) {
        flow.collect(collector)
    }
}

fun <T> Fragment.collectLatestWithLifecycle(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    action: suspend (value: T) -> Unit,
) {
    doOnLifecycle(state) {
        flow.collectLatest(action)
    }
}

private fun Fragment.doOnLifecycle(state: Lifecycle.State, onLifecycle: suspend () -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(state) {
            onLifecycle()
        }
    }
}
