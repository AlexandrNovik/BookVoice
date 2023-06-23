package com.escodro.core.extension
/*

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.VideoView
import androidx.annotation.IdRes
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import com.aimobiapps.onphone.R
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*


fun MaterialTextView.setBadgeWithArrow(context: Context, showBadge: Boolean) {
    setCompoundDrawablesWithIntrinsicBounds(
        if (showBadge) ContextCompat.getDrawable(context, R.drawable.ic_ellipse_red) else null,
        null,
        ContextCompat.getDrawable(context, R.drawable.ic_arrow_right),
        null
    )
    compoundDrawablePadding
}

fun VideoView.setRawUri(resId: Int) = setVideoURI(context.rawUriFor(resId))

fun View.fadeOut(duration: Long = 100L) {
    val fadeOut = AlphaAnimation(0.5f, 0f)
    fadeOut.interpolator = AccelerateInterpolator()
    fadeOut.duration = duration

    val animation = AnimationSet(false)
    animation.addAnimation(fadeOut)
    startAnimation(animation)
}

fun SearchView.setOnQueryTextListener(
    scope: CoroutineScope,
    debounceMs: Long = 300,
    onTextChanged: (newText: String?) -> Unit = { },
    onSubmit: (query: String?) -> Unit = { }
) = setOnQueryTextListener(object : SearchView.OnQueryTextListener {

    private var job: Job? = null

    override fun onQueryTextSubmit(query: String?): Boolean {
        onSubmit(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        job?.cancel()
        job = scope.launch {
            delay(debounceMs)
            onTextChanged(newText)
        }
        return true
    }
})

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun View.setInvisible() {
    visibility = View.INVISIBLE
}

fun EditText.addOnChangeTextListener(
    scope: CoroutineScope,
    debounceMs: Long = 300,
    onTextChanged: (newText: String) -> Unit = { }
) = object : TextWatcher {

    private var job: Job? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        job?.cancel()
        job = scope.launch {
            delay(debounceMs)
            onTextChanged(s?.toString() ?: "")
        }
    }

    override fun afterTextChanged(s: Editable?) {}
}.also(::addTextChangedListener)

fun EditText.addOnChangeTextListener(
    onTextChanged: (newText: String) -> Unit = { }
) = object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable?) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged(s?.toString() ?: "")
    }
}.also(::addTextChangedListener)

fun EditText.seekCursorToEnd() = setSelection(text.length)

fun View.openKeyboard() {
    requestFocus()
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputMethodManager?.showSoftInput(
        this,
        InputMethodManager.SHOW_FORCED,
        null
    )
}

val View.isKeyboardVisible: Boolean
    get() {
        return WindowInsetsCompat
            .toWindowInsetsCompat(rootWindowInsets)
            .isVisible(WindowInsetsCompat.Type.ime())
    }

fun View.hideKeyboard() {
    clearFocus()
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
}

fun <ViewT : View> Activity.bindView(@IdRes idRes: Int): Lazy<ViewT> {
    return lazy(LazyThreadSafetyMode.NONE) {
        findViewById<ViewT>(idRes)
    }
}

fun View.setSpamClickListener(action: () -> Unit) {
    var prevTime = Date().time
    var counter = 0
    setOnClickListener {
        val now = Date().time
        if (now - prevTime < 500) counter++ else counter = 0
        if (counter == 15) action().also { counter = 0 }
        prevTime = now
    }
}

var timer = 0L
fun debounce(ms: Int = 1000, action: () -> Unit) {
    @Suppress("SENSELESS_COMPARISON")
    if (System.currentTimeMillis() - timer < ms && timer != 0L) {
        return
    }
    timer = System.currentTimeMillis()
    action()
}

fun View?.setTimeoutClickListener(timeout: Long = 3000, action: () -> Unit) {
    try {
        this?.setOnClickListener {
            isEnabled = false
            action()
            postDelayed({ isEnabled = true }, timeout)
        }
    } catch (e: Exception) {
        Timber.e(e, "Error on setTimeoutClickListener method")
    }
}
*/
