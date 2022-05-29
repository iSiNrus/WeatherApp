package ru.barsik.weatherapp.repo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.barsik.weatherapp.repo.model.WeatherLocation

@Database(entities = [WeatherLocation::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun locationDAO() : LocationDAO

}