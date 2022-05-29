package ru.barsik.weatherapp.repo.server

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.media.Image
import android.media.ImageReader
import io.reactivex.rxjava3.core.Single
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import java.io.IOException

class ServerCommunicator(private val mService: ApiService) {

    //Weather
    private val apiKey = "985c1e62b23e99303dc3cc52624f6c8a"

    fun getLocations(query: String) : Single<Response<List<LocationServer>>> {
       return  mService.getLocations(query, 10, apiKey)
    }

    fun getCurrentWeather(lat: Double, lon:Double): Single<Response<CurrentWeatherServer>>{
        return mService.getCurrentWeather(lat, lon, apiKey)
    }

    fun getOneReqWeather(lat: Double, lon: Double): Single<Response<OneReqWeather>>{
        return mService.getOneReqWeather(lat, lon, apiKey)
    }

    fun getIcon(iconName: String): Single<Bitmap>{
        return Single.create {
            val client = OkHttpClient()
            val req = Request.Builder().url("http://openweathermap.org/img/wn/$iconName").build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.onError(e)
                }
                override fun onResponse(call: Call, response: okhttp3.Response) {
                    val btm = BitmapFactory.decodeStream(response.body?.byteStream())
                    it.onSuccess(btm)
                }
            })
        }
    }
}