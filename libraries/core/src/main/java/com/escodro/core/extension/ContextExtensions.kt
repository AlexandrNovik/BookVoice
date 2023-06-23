@file:JvmName("extension-context")

package com.escodro.core.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import logcat.LogPriority
import logcat.asLog
import logcat.logcat
import java.io.ByteArrayOutputStream
import java.util.Calendar

private const val InvalidVersion = "x.x.x"

/**
 * Sets a alarm using [AlarmManagerCompat] to be triggered based on the given parameter.
 *
 * **This function can only be called if the permission `SCHEDULE_EXACT_ALARM` is granted to
 * the application.**
 *
 * @see [android.Manifest.permission.SCHEDULE_EXACT_ALARM]
 *
 * @param triggerAtMillis time in milliseconds that the alarm should go off, using the
 * appropriate clock (depending on the alarm type).
 * @param operation action to perform when the alarm goes off
 * @param type type to define how the alarm will behave
 */
fun Context.setExactAlarm(
    triggerAtMillis: Long,
    operation: PendingIntent?,
    type: Int = AlarmManager.RTC_WAKEUP,
) {
    val currentTime = Calendar.getInstance().timeInMillis
    if (triggerAtMillis <= currentTime) {
        logcat(LogPriority.WARN) { "It is not possible to set alarm in the past" }
        return
    }

    if (operation == null) {
        logcat(LogPriority.ERROR) { "PendingIntent is null" }
        return
    }

    val manager = getAlarmManager()
    manager?.let {
        AlarmManagerCompat
            .setExactAndAllowWhileIdle(it, type, triggerAtMillis, operation)
    }
}

/**
 * Cancels a alarm set on [AlarmManager], based on the given [PendingIntent].
 *
 * @param operation action to be canceled
 */
fun Context.cancelAlarm(operation: PendingIntent?) {
    if (operation == null) {
        logcat(LogPriority.ERROR) { "PendingIntent is null" }
        return
    }

    val manager = getAlarmManager()
    manager?.let { manager.cancel(operation) }
}

/**
 * Gets string from color in format "#XXXXXX".
 *
 * @param colorRes the color resource id
 *
 * @return string from color in format "#XXXXXX"
 */
@SuppressLint("ResourceType")
fun Context.getStringColor(@ColorRes colorRes: Int): String =
    resources.getString(colorRes)

/**
 * Opens the given url in string format.
 *
 * @param url the url in string format
 */
fun Context.openUrl(url: String) {
    with(Intent(Intent.ACTION_VIEW)) {
        this.data = url.toUri()
        startActivity(this)
    }
}

/**
 * Returns the version name of the application.
 *
 * @return the version name of the application.
 */
@Suppress("Deprecation") // SDK below Tiramisu needs to access the deprecated version
fun Context.getVersionName(): String {
    var packageInfo: PackageInfo? = null
    packageName.let {
        try {
            packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(it, PackageManager.PackageInfoFlags.of(0))
            } else {
                packageManager.getPackageInfo(it, 0)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            logcat(LogPriority.ERROR) { e.asLog() }
        }
    }
    return packageInfo?.versionName ?: InvalidVersion
}


fun Context.openGeneralSettings() {
    val i = Intent().apply {
        action = Settings.ACTION_SETTINGS
        addCategory(Intent.CATEGORY_DEFAULT)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    }
    startActivity(i)
}

fun Context.rawUriFor(resId: Int): Uri =
    Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(packageName)
        .appendPath("$resId")
        .build()

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.copyToClipboard(message: CharSequence, success: () -> Unit = {}) {
    ContextCompat.getSystemService(this, ClipboardManager::class.java)?.let { manager ->
        val clip = ClipData.newPlainText("mobiappdata", message)
        manager.setPrimaryClip(clip)
        success()
    }
}

fun Context.pasteFromClipboard(): String? =
    ContextCompat.getSystemService(this, ClipboardManager::class.java)?.let { manager ->
        manager.primaryClip?.getItemAt(0)?.text?.toString()
    }

fun Activity.shareText(message: CharSequence) {
    val i = ShareCompat.IntentBuilder(this)
        .setType("text/plain")
        .setText(message)
        .createChooserIntent()
        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    startActivity(i)
}

fun Context.shareBitmap(bitmap: Bitmap) {
    val i = ShareCompat.IntentBuilder(this)
        .setType("image/jpeg")
        .setStream(getImageUri(applicationContext, bitmap)) // TODO: get rid of deprecated
        .createChooserIntent()
        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    startActivity(i)
}

fun getImageUri(context: Context, bitmap: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bytes)
    val path =
        MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "", null)
    return Uri.parse(path)
}
