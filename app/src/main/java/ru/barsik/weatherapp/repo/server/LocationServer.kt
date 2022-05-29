package ru.barsik.weatherapp.repo.server

data class LocationServer(
    var name: String,
    var localNames: LocalNames?,
    var lat: Double,
    var lon: Double,
    var country: String,
    var state: String?
)

data class LocalNames(
//    val vi: String?,
//    val feature_name: String?,
//    val ko: String?,
//    val ca: String?,
//    val ml: String?,
//    val ja: String?,
//    val fa: String?,
//    val asii: String?,
//    val sk: String?,
//    val fr: String?,
//    val de: String?,
//    val hi: String?,
//    val zh: String?,
//    val cs: String?,
//    val pt: String?,
//    val eo: String?,
//    val et: String?,
//    val si: String?,
//    val da: String?,
    val ru: String?
//    val bo: String?,
//    val kn: String?,
//    val en: String?,
//    val ar: String?,
//    val sv: String?
)