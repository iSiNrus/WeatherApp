package ru.barsik.weatherapp.repo.server

data class CurrentWeatherServer (
    var coord: Coord? = null,
    var weather: ArrayList<Weather>? = null,
    var base: String? = null,
    var main: Main? = null,
    var visibility: Int = 0,
    var wind: Wind? = null,
    var clouds: Clouds? = null,
    var dt: Int = 0,
    var sys: Sys? = null,
    var timezone: Int = 0,
    var id: Int = 0,
    var name: String? = null,
    var cod: Int = 0
)

data class Clouds(
    var all: Double = 0.0
)

data class Coord (
    var lon: Double = 0.0,
    var lat: Double = 0.0
    )

data class Main (
    var temp: Double = 0.0,
    var feels_like: Double = 0.0,
    var temp_min: Double = 0.0,
    var temp_max: Double = 0.0,
    var pressure: Int = 0,
    var humidity: Int = 0
)

data class Sys (
    var type: Int = 0,
    var id: Int = 0,
    var message: Double = 0.0,
    var country: String? = null,
    var sunrise: Int = 0,
    var sunset: Int = 0
)

data class Wind (
    var speed: Double = 0.0,
    var deg: Int = 0
)