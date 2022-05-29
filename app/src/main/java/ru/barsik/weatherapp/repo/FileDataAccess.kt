package ru.barsik.weatherapp.repo

import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.barsik.weatherapp.repo.model.WeatherLocation
import java.io.*

class FileDataAccess(private val application: Application) {

    private var locatList : MutableList<WeatherLocation>? = null

    fun getLocatList(): List<WeatherLocation>{
        return locatList ?: readLocations()
    }

    private fun readLocations(): List<WeatherLocation>{
        val br = BufferedReader(InputStreamReader(application.assets.open("locals.json")))
        var s: String? = ""
        val jsonString = StringBuilder()

        while (s != null) {
            s = br.readLine()
            jsonString.append(s ?: "")
        }
        br.close()
        val gson = Gson()
        val locatType = object : TypeToken<List<WeatherLocation>>() {}.type
        locatList = gson.fromJson(jsonString.toString(), locatType) as MutableList<WeatherLocation>
        return locatList as List<WeatherLocation>
    }

    fun addNewLocation(nLocat : WeatherLocation){
        if(locatList==null) readLocations()
        locatList?.add(nLocat)

    }
}