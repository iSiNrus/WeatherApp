package ru.barsik.weatherapp.repo.server

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/geo/1.0/direct")
    fun getLocations(
        @Query("q") q : String,
        @Query("limit") limit: Int,
        @Query("apikey") apikey: String
    ) : Single<Response<List<LocationServer>>>

    @GET("/data/2.5/weather")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("apikey") apikey: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ua"
    ) : Single<Response<CurrentWeatherServer>>

    @GET("/data/2.5/onecall")
    fun getOneReqWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("apikey") apikey: String,
        @Query("exclude") exclude: String = "minutely,alerts",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru"
    ) : Single<Response<OneReqWeather>>

}