package ru.barsik.weatherapp.repo.database

import androidx.room.*
import io.reactivex.rxjava3.core.Single
import ru.barsik.weatherapp.repo.model.WeatherLocation

@Dao
interface LocationDAO {

    @Query("SELECT * FROM weatherlocation")
    fun getAllLocations() : Single<List<WeatherLocation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLocation(location: WeatherLocation) : Single<Long>

    @Query("DELETE FROM weatherlocation WHERE id=:locId")
    fun deleteLocationById(locId: Int) : Single<Int>

    @Delete(entity = WeatherLocation::class)
    fun deleteLocation(wl: WeatherLocation) :Single<Int>
}