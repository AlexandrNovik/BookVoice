package com.escodro.core.extension

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Size
import java.util.*
import kotlin.math.min

fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
    edit().run {
        action()
        apply()
    }
}

fun <T> Bundle.parcelable(key: String, clazz: Class<T>): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, clazz)
    } else {
        getParcelable(key)
    }

fun Long.millisecondToTimeString(): String {
    return this.div(1000).secondsToTimeString()
}

fun Long.secondsToTimeString(): String {
    val hours = this / 3600
    return if (hours > 0) {
        String.format(
            Locale.getDefault(),
            "%d:%02d:%02d",
            hours,
            (this % 3600) / 60,
            this % 60
        )
    } else {
        String.format(
            Locale.getDefault(),
            "%02d:%02d",
            (this % 3600) / 60,
            this % 60
        )
    }
}

infix fun Int.stringIntervalTo(to: Int): String = if (this != to) {
    "$this - $to"
} else {
    this.toString()
}

infix fun Size.scaleWith(dimension: Int): Size {
    val ratio = min(dimension.toFloat() / width, dimension.toFloat() / height)
    return Size((width * ratio).toInt(), (height * ratio).toInt())
}
