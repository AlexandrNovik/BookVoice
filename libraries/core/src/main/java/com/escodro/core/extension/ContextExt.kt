package com.escodro.core.extension

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream


inline fun doIfSdkMoreThen26(doAction: () -> Unit) {
    if (isSdkMoreThen26()) {
        doAction()
    }
}

fun isSdkMoreThen26() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

/*
fun Context.openExternalSettings() {
    val i = Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        addCategory(Intent.CATEGORY_DEFAULT)
        data = Uri.parse("package:" + BuildConfig.APPLICATION_ID)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    }
    startActivity(i)
}
*/

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

/*fun LifecycleOwner.repeatOnResumed(action: () -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            action()
        }
    }
}*/
