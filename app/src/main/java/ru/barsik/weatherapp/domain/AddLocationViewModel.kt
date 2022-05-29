package ru.barsik.weatherapp.domain

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import ru.barsik.weatherapp.repo.AppRepository
import ru.barsik.weatherapp.repo.model.WeatherLocation
import ru.barsik.weatherapp.repo.server.LocationServer

class AddLocationViewModel(
    application: Application,
    private var mRepository: AppRepository) : ViewModel() {
    private val TAG = "AddLocationViewModel"
    private var suggestListLiveData = MutableLiveData(ArrayList<String>())
    private var saveStatusLD = MutableLiveData<Long>(0L)
    private var arrayResponse = ArrayList<LocationServer>()

    fun findSuggestions(query: String){
        suggestListLiveData.value?.clear()
       mRepository.getLocationsFromApi(query).subscribe({ item ->
           arrayResponse = (item.body()?: ArrayList()) as ArrayList<LocationServer>
           Log.d(TAG, "findSuggestions: ${arrayResponse.size}")
           if (arrayResponse.isNotEmpty()) {
               var resultArray = ArrayList<String>()
               arrayResponse.forEach {
                   var resString = (it.localNames?.ru ?: it.name) + ", " + it.state + ", " + it.country
                   resultArray.add(resString)
               }
               suggestListLiveData.value = resultArray
           }
       }, {
           Log.d(TAG, "onError: ${it.message}")
       })
    }

    fun getSuggestListLD() = suggestListLiveData
    fun getSaveStatusLD() = saveStatusLD

    fun addLocation(position: Int) {
        var locSer = arrayResponse[position]
        var location = WeatherLocation(0, locSer.name, locSer.state, locSer.country, locSer.lon, locSer.lat)
        mRepository.addLocation(location)
            .subscribe({
                Log.d(TAG, "addLocation: $it")
                saveStatusLD.value = it
                saveStatusLD.value = 0L
        },{
                Log.d(TAG, "addLocation: ${it.message}")
                saveStatusLD.value = -1
                saveStatusLD.value = 0L
        })
    }

}