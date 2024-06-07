package com.practicum.movieexample.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> debounce(
    delayMillisLong: Long,
    coroutineScope: CoroutineScope,
    useLastParam: Boolean,
    action: (T) -> Unit
): (T) -> Unit {
        var debounceJob: Job? = null
        return {param: T ->
            if (useLastParam) {
                debounceJob?.cancel()
            }
            if (debounceJob?.isCompleted != false || useLastParam) {
                debounceJob = coroutineScope.launch {
                    delay(delayMillisLong)
                    action(param)
                }
            }
        }
}