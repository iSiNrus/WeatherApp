package ru.barsik.weatherapp.repo.server

class OneReqWeather {
   var lat = 0.0
   var lon = 0.0
   var timezone: String? = null
   var timezone_offset = 0
   var current: Current? = null
   var minutely: ArrayList<Minutely>? = null
   var hourly: ArrayList<Hourly>? = null
   var daily: ArrayList<Daily>? = null
   var alerts: ArrayList<Alert>? = null
}

class Alert {
   var sender_name: String? = null
   var event: String? = null
   var start = 0
   var end = 0
   var description: String? = null
   var tags: ArrayList<String>? = null
}

class Current {
   var dt = 0L
   var sunrise = 0
   var sunset = 0
   var temp = 0.0
   var feels_like = 0.0
   var pressure = 0
   var humidity = 0
   var dew_point = 0.0
   var uvi = 0.0
   var clouds = 0
   var visibility = 0
   var wind_speed = 0f
   var wind_deg = 0
   var weather: ArrayList<Weather>? = null
   var rain: Rain? = null
}

class Daily {
   var dt = 0L
   var sunrise = 0
   var sunset = 0
   var moonrise = 0
   var moonset = 0
   var moon_phase = 0.0
   var temp: Temp? = null
   var feels_like: FeelsLike? = null
   var pressure = 0
   var humidity = 0
   var dew_point = 0.0
   var wind_speed = 0.0
   var wind_deg = 0
   var weather: ArrayList<Weather>? = null
   var clouds = 0
   var pop = 0.0
   var rain = 0.0
   var uvi = 0.0
}

class FeelsLike {
   var day = 0.0
   var night = 0.0
   var eve = 0.0
   var morn = 0.0
}

class Hourly {
   var dt = 0L
   var temp = 0.0
   var feels_like = 0.0
   var pressure = 0
   var humidity = 0
   var dew_point = 0.0
   var uvi = 0.0
   var clouds = 0
   var visibility = 0
   var wind_speed = 0.0
   var wind_deg = 0
   var wind_gust = 0.0
   var weather: ArrayList<Weather>? = null
   var pop = 0.0
}

class Minutely {
   var dt = 0
   var precipitation = 0.0
}

class Rain {
   var _1h = 0.0
}

class Temp {
   var day = 0.0
   var min = 0.0
   var max = 0.0
   var night = 0.0
   var eve = 0.0
   var morn = 0.0
}

class Weather {
   var id = 0
   var main: String? = null
   var description: String? = null
   var icon: String? = null
}

