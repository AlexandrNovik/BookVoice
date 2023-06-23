package com.escodro.core.extension

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun Date.calculateTimeBetweenNow(
    now: Date, textDays: String, textHours: String, textMinutes: String
): String {
    val isToday = calculateHoursBetween(now) < 24
    return if (isToday) {
        val minutes = calculateMinutesBetween(now)
        val hours = calculateHoursBetween(now)
        return if (minutes >= 60) "$hours $textHours" else "${if (minutes < 0) 0 else minutes} $textMinutes"
    } else {
        val days = calculateDaysBetween(now)
        "$days $textDays"
    }
}

fun Date.calculateDaysBetween(date: Date) = TimeUnit.DAYS.convert(
    time - date.time, TimeUnit.MILLISECONDS
)

fun Date.calculateMinutesBetween(date: Date) = TimeUnit.MINUTES.convert(
    time - date.time, TimeUnit.MILLISECONDS
)

fun Date.calculateHoursBetween(date: Date) = TimeUnit.HOURS.convert(
    time - date.time, TimeUnit.MILLISECONDS
)

fun Date.equalsWithoutTime(date: Date): Boolean =
    SimpleDateFormat("yyyyMMdd", Locale.getDefault()).let { format ->
        format.format(this) == format.format(date)
    }

/*@SuppressLint("SimpleDateFormat")
fun Date.format(): String = SimpleDateFormat(TIME_FORMAT).format(this)*/
