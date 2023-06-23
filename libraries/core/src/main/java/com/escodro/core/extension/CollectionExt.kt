package com.escodro.core.extension

val <K, V> Map<K, V>.asString: String
    get() = entries.joinToString { "${it.key}: ${it.value}\n" }
