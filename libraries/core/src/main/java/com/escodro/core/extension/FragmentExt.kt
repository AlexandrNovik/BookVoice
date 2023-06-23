package com.escodro.core.extension

/*
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ShareCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.aimobiapps.onphone.R
import com.aimobiapps.onphone.data.network.exceptions.*
import com.aimobiapps.onphone.domain.model.RegulationField
import com.aimobiapps.onphone.ui.fragment.abstraction.BaseFragment
import kotlinx.coroutines.launch
import timber.log.Timber

fun Fragment.findTopNavController(): NavController {
    val topLevelHost =
        requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}

fun BaseFragment.shareText(text: String) =
    runCatching { requireActivity().shareText(text) }.getOrElse(::showError)

fun Fragment.setUpSearch(toolbar: Toolbar?, search: suspend (String) -> Unit) {
    try {
        toolbar?.inflateMenu(R.menu.menu_search)
        val searchView = toolbar?.menu?.findItem(R.id.search)?.actionView as SearchView?
        searchView?.setOnQueryTextListener(lifecycleScope, onTextChanged = { query ->
            if (view != null) {
                viewLifecycleOwner.lifecycleScope.launch {
                    search(query ?: EMPTY)
                }
            }
        })
        (searchView?.findViewById(androidx.appcompat.R.id.search_src_text) as EditText?)?.apply {
            hint = getString(R.string.search)
        }
    } catch (e: IllegalStateException) {
        Timber.e(e)
    }
}

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    context?.let { context ->
        Toast.makeText(context, text, duration).show()
    }
}

fun Fragment.toast(@StringRes textResId: Int, duration: Int = Toast.LENGTH_SHORT) {
    context?.let { context ->
        Toast.makeText(context, textResId, duration).show()
    }
}

fun BaseFragment.shareAudio(uri: Uri?) = runCatching {
    val intent = ShareCompat.IntentBuilder(requireActivity())
        .setType("audio/*")
        .setChooserTitle(safeString(R.string.share_record))
        .setStream(uri)
        .createChooserIntent().addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    startActivity(intent)
}.getOrElse(::showError)

fun Fragment.getStringIdentifier(resIdAsString: String?): Int? = try {
    resources.getIdentifier(
        resIdAsString,
        "string",
        requireContext().packageName
    ).takeIf { it != 0 }
} catch (e: Exception) {
    null
}

fun Fragment.getString(resIdAsString: String): String? = try {
    getStringIdentifier(resIdAsString)?.let(::getString)
} catch (e: Exception) {
    null
}

fun Context.getStringIdentifier(resIdAsString: String): Int? = try {
    resources.getIdentifier(resIdAsString, "string", packageName).takeIf { it != 0 }
} catch (e: Exception) {
    null
}

fun Context.getString(resIdAsString: String): String? = try {
    getStringIdentifier(resIdAsString)?.let(::getString)
} catch (e: Exception) {
    null
}

fun Context.getString(name: String, params: Array<out String>): String? =
    try {
        getString(resources.getIdentifier(name, "string", packageName), *params)
    } catch (e: Exception) {
        null
    }

fun Fragment.handleNetworkException(exception: NetworkException) {
    when (exception) {
        is NoInternetConnectionException -> toast(
            R.string.no_internet_connection,
            Toast.LENGTH_LONG
        )
        is ServerInternalErrorException -> toast(R.string.internal_server_error)
        is TooManyTriesException -> toast(R.string.too_many_tries)
        is UnauthorizedException -> toast(R.string.unauthorized)
        is PayloadTooLargeException -> toast(R.string.payload_too_large)
        is ForbiddenException -> findTopNavController().navigateSafe(R.id.action_global_paywallAllPeriods)
        else -> toast(R.string.unknown_error)
    }
}

fun Fragment.handleServerErrorMessage(resIdAsString: String?) {
    toast(getStringIdentifier(resIdAsString) ?: R.string.unknown_error)
}

fun Fragment.createProgressDialog() = AlertDialog.Builder(requireContext())
    .setCancelable(false)
    .setView(View.inflate(requireContext(), R.layout.view_progress_bar, null))
    .create().apply {
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

fun Fragment.safeString(resId: Int, arg: String?): String {
    return try {
        resources.getString(resId, arg)
    } catch (e: IllegalStateException) {
        EMPTY
    }
}

fun Fragment.safeString(resId: Int): String {
    return try {
        resources.getString(resId)
    } catch (e: IllegalStateException) {
        EMPTY
    }
}

fun Fragment.createYesNoDialog(
    @StringRes titleRes: Int? = null,
    @StringRes messageRes: Int? = null,
    @StringRes yesTextRes: Int? = null,
    @StringRes noTextRes: Int? = null,
    onYesClick: () -> Unit = {},
    onNoClick: () -> Unit = {}
) = AlertDialog.Builder(requireContext()).apply {
    titleRes?.let(::setTitle)
    messageRes?.let(::setMessage)
    setPositiveButton(yesTextRes ?: R.string.yes) { dialog, _ ->
        onYesClick()
        dialog.dismiss()
    }
    setNegativeButton(noTextRes ?: R.string.no) { dialog, _ ->
        onNoClick()
        dialog.dismiss()
    }
}

fun Fragment.getLocalizedNameFor(field: RegulationField): String {
    val details = "personal_details__"
    val f = if (field.isPersonal) getString("$details${field.name}") else getString(field.name)
    return f ?: field.name
}

fun Fragment.showEditTextDialog(
    @StringRes titleRes: Int? = null,
    @StringRes editTextHint: Int? = null,
    editTextText: String? = null,
    onOkClick: (String) -> Unit = {}
): AlertDialog {
    return AlertDialog.Builder(requireContext())
        .apply {
            titleRes?.let(::setTitle)
            val view = View.inflate(context, R.layout.view_edit_text, null)
            val editText = view.findViewById<EditText>(R.id.edit_text)
                .apply {
                    editTextHint?.let(::setHint)
                    editTextText?.let(::setText)
                }
            setView(view)
            setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
                onOkClick(editText.text.toString())
            }
            setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
        }
        .show()
}

val Context.fileProviderAuthority: String
    get() = "$packageName.provider"

fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * percent
    dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
}

fun DialogFragment.setWidthAndHeightPercent(widthPercentage: Int, heightPercentage: Int) {
    val wPercent = widthPercentage.toFloat() / 100
    val hPercent = heightPercentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * wPercent
    val percentHeight = rect.height() * hPercent
    dialog?.window?.setLayout(percentWidth.toInt(), percentHeight.toInt())
}
*/
