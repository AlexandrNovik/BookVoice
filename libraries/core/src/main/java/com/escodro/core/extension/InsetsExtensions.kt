package com.escodro.core.extension

import android.view.View
import android.view.ViewGroup
import androidx.core.view.*

fun View.setVerticalInsetsPadding() {
    val baseTopPadding = paddingTop
    val baseBottomPadding = paddingBottom
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemInsets = insets.getInsets(
            WindowInsetsCompat.Type.systemBars() or
                    WindowInsetsCompat.Type.ime()
        )

        view.updatePadding(
            top = systemInsets.top + baseTopPadding,
            bottom = systemInsets.bottom + baseBottomPadding
        )

        insets
    }
}

fun View.setBottomInsetsPadding() {
    val baseBottomPadding = paddingBottom
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemInsets = insets.getInsets(
            WindowInsetsCompat.Type.systemBars() or
                    WindowInsetsCompat.Type.ime()
        )
        view.updatePadding(
            bottom = systemInsets.bottom + baseBottomPadding
        )
        insets
    }
}

fun View.setTopInsetsPadding() {
    val baseTopPadding = paddingTop
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemInsets = insets.getInsets(
            WindowInsetsCompat.Type.systemBars() or
                    WindowInsetsCompat.Type.ime()
        )
        view.updatePadding(
            top = systemInsets.top + baseTopPadding
        )
        insets
    }
}

fun View.setTopInsetsMargin() {
    val baseMargin = marginTop
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemInsets = insets.getInsets(
            WindowInsetsCompat.Type.systemBars() or
                    WindowInsetsCompat.Type.ime()
        )
        val params = view.layoutParams
        if (params is ViewGroup.MarginLayoutParams) {
            params.updateMargins(top = baseMargin + systemInsets.top)
        }

        insets
    }
}

fun View.setBottomInsetsMargin() {
    val baseMargin = marginBottom
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemInsets = insets.getInsets(
            WindowInsetsCompat.Type.systemBars() or
                    WindowInsetsCompat.Type.ime()
        )
        val params = view.layoutParams
        if (params is ViewGroup.MarginLayoutParams) {
            params.updateMargins(bottom = baseMargin + systemInsets.bottom)
        }

        insets
    }
}
