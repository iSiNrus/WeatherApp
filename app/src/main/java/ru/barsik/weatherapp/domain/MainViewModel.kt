package ru.barsik.weatherapp.domain

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.barsik.weatherapp.repo.AppRepository
import ru.barsik.weatherapp.repo.model.WeatherLocation
import ru.barsik.weatherapp.repo.server.CurrentWeatherServer
import ru.barsik.weatherapp.repo.server.OneReqWeather

class MainViewModel(
    application: Application,
    private var mRepository: AppRepository
) : ViewModel() {

    private val TAG = "MainViewModel"
    private var locationsListLiveData = MutableLiveData<ArrayList<WeatherLocation>>()
    private var selectedLocation: WeatherLocation? = null
    private var selectedLocationPos: Int? = null
    private var currentWeatherLiveData = MutableLiveData<CurrentWeatherServer>()
    private var oneReqWeatherLiveData = MutableLiveData<OneReqWeather>()

    fun getLocationsLD() = locationsListLiveData
    fun getCurrentWeatherLD() = currentWeatherLiveData
    fun getOneReqWeatherLD() = oneReqWeatherLiveData
    fun getSelectedLocationPos() = selectedLocationPos
    fun getSelectedLocation() = selectedLocation

    fun getLocations() {
        mRepository.getLocationList().subscribe({ list ->

            var arrayList = ArrayList<WeatherLocation>()
            list.forEach {
                arrayList.add(it)
            }
            locationsListLiveData.value = arrayList
        }, {
            Log.d(TAG, "getLocations: ${it.message}")
        })
    }

    fun oneReqWeather() {
        if (selectedLocation != null)
            mRepository.getOneReqWeather(selectedLocation!!).subscribe({
                if (it.body() != null) {
                    oneReqWeatherLiveData.postValue(it.body())
                }
            }, {
                Log.d(TAG, "setSelectedLocationPos: ${it.message}")
            })
    }

    fun setSelectedLocation(pos: Int) {
        selectedLocationPos = pos
        selectedLocation = locationsListLiveData.value?.get(pos)
        oneReqWeather()
    }

    fun deleteLocation(pos: Int) {
        selectedLocation = null
        selectedLocationPos = null

        mRepository.deleteLocation(locationsListLiveData.value?.get(pos))?.subscribe { it ->
            getLocations()
        }
    }
}