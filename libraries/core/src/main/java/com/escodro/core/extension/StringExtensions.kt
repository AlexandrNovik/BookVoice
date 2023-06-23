package com.escodro.core.extension

import android.content.Context
import android.telephony.PhoneNumberUtils
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern

const val EMPTY = ""
const val SPACE = " "
const val NEW_LINE = "\n"
const val PLUS = "+"
const val DASH = "-"
const val COMMA = ","
const val TIME_FORMAT = "dd.MM.yyyy, HH:mm:ss aaa"

fun String.removeChars(symbols: String) = filterNot { symbols.indexOf(it) > -1 }
fun String.simplifyPhoneNumber() = removeChars("() -")
fun String.correctNumber(): String {
    val correctedString = removeChars("() -")
    val containPlus = correctedString.firstOrNull()?.let { it == '+' } ?: false
    return if (containPlus) correctedString
    else "$PLUS$correctedString"
}

fun String.formatNumber(isoCode: String? = null): String {
    val code = if (isoCode != null && isoCode == "XK") {
        "XXK"
    } else {
        isoCode
    }
    return PhoneNumberUtils.formatNumber(
        this, code ?: Locale.getDefault().isO3Country
    ) ?: this
}

fun String.search(query: String): Boolean =
    this.replace("[$DASH\\s]".toRegex(), EMPTY).trim().contains(query.trim(), true)

fun getAssetsPath(subPath: String) = "file:///android_asset/$subPath"

fun String.getLocaleFromCode(): Locale {
    return if (contains("-")) {
        val split = split("-")
        Locale(split.first(), split.last())
    } else {
        Locale(this)
    }
}

fun String.createLink(url: String): String {
    return """<a href="$url">$this</a>"""
}

fun String.containsVideoToken(): Boolean {
    val regex = Regex(pattern = "/v/[A-Za-z0-9_-]+\$", options = setOf(RegexOption.IGNORE_CASE))
    return regex.containsMatchIn(this)
}

fun String.extractUrls(): List<String> {
    val containedUrls: MutableList<String> = ArrayList()
    val urlRegex =
        "((https?|ftp|gopher|telnet|file|onphone):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)"
    val pattern: Pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE)
    val urlMatcher: Matcher = pattern.matcher(this)

    while (urlMatcher.find()) {
        containedUrls.add(this.substring(urlMatcher.start(0), urlMatcher.end(0)))
    }

    return containedUrls
}

fun Context.string(resId: Int): String = getString(resId)

fun Context.color(resId: Int): Int = getColor(resId)
