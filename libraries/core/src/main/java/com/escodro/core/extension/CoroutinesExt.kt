package com.escodro.core.extension

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


suspend inline fun <T> withIO(noinline block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.IO, block)
}

suspend inline fun <T> withDefault(noinline block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.Default, block)
}

suspend inline fun <T> withMain(noinline block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.Main, block)
}

/*fun CoroutineScope.launchSafe(
    block: suspend () -> Unit
): Job = launch(CoroutineExceptionHandler { _, th -> Timber.e(th) }) { block() }*/

fun CoroutineScope.launchMain(
    block: suspend () -> Unit
): Job = launch(Dispatchers.Main) { block() }

fun CoroutineScope.launchIO(
    handler: CoroutineExceptionHandler,
    block: suspend () -> Unit
): Job = launch(Dispatchers.IO + handler) { block() }

fun <T> Flow<T>.collectIn(
    scope: CoroutineScope,
    action: FlowCollector<T>
) = scope.launch {
    this@collectIn.collect(action)
}

fun <T> Flow<T>.collectIn(
    scope: CoroutineScope,
) = scope.launch {
    this@collectIn.collect {}
}

fun <T> Flow<T?>.collectNotNullIn(
    scope: CoroutineScope,
    action: suspend (T) -> Unit
) = scope.launch {
    this@collectNotNullIn.collect {
        if (it != null) action(it)
    }
}

inline fun <T> MutableStateFlow<T>.updateValue(updateAction: (T) -> T) {
    value = updateAction(value)
}

inline fun <T> MutableStateFlow<T>.updatedValue(updateAction: (T) -> T): T {
    return updateAction(value)
}

suspend inline fun <T, R> Iterable<T>.mapParallel(crossinline transform: suspend (T) -> R): List<R> {
    return coroutineScope {
        map {
            async {
                transform(it)
            }
        }.awaitAll()
    }
}
