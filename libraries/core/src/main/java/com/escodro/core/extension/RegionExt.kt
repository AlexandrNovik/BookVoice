package com.escodro.core.extension

import android.content.Context
/*

const val CUSTOM_PLAN_CODE = "custom_plan_code"
fun isCodeCustom(code: String): Boolean {
    return code == CUSTOM_PLAN_CODE
}

fun Context.nameByCountryOrRegionCode(code: String): String {
    try {
        val region = code.regionOrNull()
        if (region != null) {
            return string(region.nameId())
        }
        return getCountryNameByCode(code)
    } catch (e: Exception) {
        Timber.e(e, "Error in nameByCountryOrRegionCode method")
        return EMPTY
    }
}

fun Context.regionNameOrNull(code: String): String? {
    try {
        val region = code.regionOrNull()
        if (region != null) {
            return string(region.nameId())
        }
        return null
    } catch (e: Exception) {
        Timber.e(e, "Error in nameByCountryOrRegionCode method")
        return EMPTY
    }
}

fun Region.nameId(): Int {
    return when (this) {
        Region.Africa -> R.string.africa
        Region.Americas -> R.string.americas
        Region.Asia -> R.string.asia
        Region.Europe -> R.string.europe
        Region.Global -> R.string.global_countries
        Region.Oceania -> R.string.oceania
        else -> R.string.global_countries
    }
}

fun Region.iconId(): Int {
    return when (this) {
        Region.Africa -> R.drawable.africa
        Region.Americas -> R.drawable.americas
        Region.Asia -> R.drawable.asia
        Region.Europe -> R.drawable.europe
        Region.Global -> R.drawable.global
        Region.Oceania -> R.drawable.oceania
        else -> R.drawable.global
    }
}

fun String.regionOrNull(): Region? = esimRegions().firstOrNull { it.code == this }

fun esimRegions(): List<Region> {
    return Region::class.nestedClasses
        .filter { (it.objectInstance is Region) }
        .map { it.objectInstance as Region }
}

fun esimCountries(): List<Region.Country> {
    val countryList =
        Region.Country::class.nestedClasses.map { it.objectInstance as Region.Country }
    val caribbeanIslandsList =
        Region.CaribbeanIslands::class.nestedClasses.map { it.objectInstance as Region.CaribbeanIslands }
    return countryList + caribbeanIslandsList
}

fun String.mapCodeToImageId(): Int =
    concatAllEsimLocations().firstOrNull { it.code == this.uppercase() }?.imageId
        ?: Region.Global.imageId

fun String.mapCodeToColorId(): Int =
    concatAllEsimLocations().firstOrNull { it.code == this.uppercase() }?.colorId
        ?: Region.Global.colorId

private val regionsMap: MutableMap<Region, List<String>> = mutableMapOf()
fun Region.countryIsoCodes(): List<String> {
    return if (regionsMap[this] == null) {
        val list = esimCountries().filter { it.regionsList.contains(this) }.map { it.code }
        regionsMap[this] = list
        list
    } else {
        regionsMap[this] ?: emptyList()
    }
}

fun List<String>.codeByCountries(filter: (List<String>) -> List<String>): String {
    if (this.count() == 1) {
        return first()
    }

    if (regionsMap.keys.size != esimRegions().size) {
        fillCountryIsoCodes()
    }

    val filteredCountries = filter(this).sorted()
    if (filteredCountries.count() == 1) {
        return first()
    }
    val keys = regionsMap.keys.sortedByDescending { it.code != Region.Global.code }
    for (key in keys) {
        val filteredRegionCountries = filter(regionsMap[key]!!).sorted()
        if (filteredRegionCountries.containsAll(filteredCountries)) return key.code
    }
    return firstOrNull() ?: EMPTY
}

suspend fun warmUpCountryIsoCodes(): Unit = withDefault {
    fillCountryIsoCodes()
}

fun List<String>.sortByCountry(): List<String> {
    val canadaName = getCountryNameByCode(Region.Country.Canada.code)
    val usaName = getCountryNameByCode(Region.Country.Usa.code)
    return sortedBy { getCountryNameByCode(it) }
        .sortedByDescending { getCountryNameByCode(it) == canadaName }
        .sortedByDescending { getCountryNameByCode(it) == usaName }
}

private fun fillCountryIsoCodes() {
    val regions = esimRegions()
    if (regionsMap.keys.size != regions.size) {
        esimRegions().forEach { it.countryIsoCodes() }
    }
}

private fun concatAllEsimLocations(): List<Region> {
    return esimRegions() + esimCountries()
}
*/
