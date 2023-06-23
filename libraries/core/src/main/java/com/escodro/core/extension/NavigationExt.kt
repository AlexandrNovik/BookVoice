/*
package com.escodro.core.extension

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun NavController.navigateSafe(directions: NavDirections) {
    val action = currentDestination?.getAction(directions.actionId)
    if (action != null) {
        navigate(directions)
    }
}

fun NavController.navigateSafe(
    @IdRes actionId: Int,
    args: Bundle? = null
) {
    val action = currentDestination?.getAction(actionId)
    if (action != null) {
        navigate(actionId, args)
    }
}

fun <T> Fragment.getNavigationResult(key: String): MutableLiveData<T>? =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData(key)

fun <T> Fragment.setNavigationResult(key: String, value: T) =
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, value)

fun <T> Fragment.getTopNavigationResult(key: String): MutableLiveData<T>? =
    findTopNavController().currentBackStackEntry?.savedStateHandle?.getLiveData(key)
*/
