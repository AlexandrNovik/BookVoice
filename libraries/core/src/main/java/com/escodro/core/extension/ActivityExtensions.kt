package com.escodro.core.extension

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes


fun Activity.toast(@StringRes textResId: Int, duration: Int = Toast.LENGTH_SHORT) {
    this.let { context ->
        Toast.makeText(context, textResId, duration).show()
    }
}

/*
fun Activity.handleNetworkException(exception: NetworkException) {
    when (exception) {
        is NoInternetConnectionException -> toast(R.string.no_internet_connection)
        is ServerInternalErrorException -> toast(R.string.internal_server_error)
        is TooManyTriesException -> toast(R.string.too_many_tries)
        is UnauthorizedException -> toast(R.string.unauthorized)
        is PayloadTooLargeException -> toast(R.string.payload_too_large)
        else -> toast(R.string.unknown_error)
    }
}
*/

/*
fun Activity.handleServerErrorMessage(resIdAsString: String?) {
    toast(getStringIdentifier(resIdAsString) ?: R.string.unknown_error)
}
*/


fun Activity.getStringIdentifier(resIdAsString: String?): Int? = try {
    resources.getIdentifier(
        resIdAsString,
        "string",
        applicationContext.packageName
    ).takeIf { it != 0 }
} catch (e: Exception) {
    null
}
/*

fun Activity.triggerRestart() {
    val intent = Intent(this, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    ProcessPhoenix.triggerRebirth(this.applicationContext, intent)
}
*/
