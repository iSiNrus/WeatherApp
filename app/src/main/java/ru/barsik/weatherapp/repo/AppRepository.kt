package ru.barsik.weatherapp.repo

import android.graphics.Bitmap
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import ru.barsik.weatherapp.repo.database.AppDatabase
import ru.barsik.weatherapp.repo.model.WeatherLocation
import ru.barsik.weatherapp.repo.server.CurrentWeatherServer
import ru.barsik.weatherapp.repo.server.LocationServer
import ru.barsik.weatherapp.repo.server.OneReqWeather
import ru.barsik.weatherapp.repo.server.ServerCommunicator

class AppRepository(private val db: AppDatabase, private val communicator: ServerCommunicator) {

    fun getLocationList(): Single<List<WeatherLocation>> {
        return db.locationDAO().getAllLocations()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getLocationsFromApi(query: String): Single<Response<List<LocationServer>>> {
        return communicator.getLocations(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addLocation(location: WeatherLocation): Single<Long> {
        return db.locationDAO().addLocation(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCurrentWeather(weatherLocation: WeatherLocation) : Single<Response<CurrentWeatherServer>>{
        return communicator.getCurrentWeather(weatherLocation.lat, weatherLocation.lon)
    }

    fun getOneReqWeather(weatherLocation: WeatherLocation) : Single<Response<OneReqWeather>>{
        return communicator.getOneReqWeather(weatherLocation.lat, weatherLocation.lon)
    }

    fun getWeatherBitmap(iconName: String): Single<Bitmap>{
        return communicator.getIcon(iconName)
    }

    fun deleteLocation(weatherLoc: WeatherLocation?) : Single<Int>? {
        return if(weatherLoc != null)
            db.locationDAO().deleteLocation(weatherLoc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        else null
    }
}