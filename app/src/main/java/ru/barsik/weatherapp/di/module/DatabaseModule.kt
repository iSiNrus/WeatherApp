package ru.barsik.weatherapp.di.module

import dagger.Module
import dagger.Provides
import ru.barsik.weatherapp.repo.database.AppDatabase

@Module
class DatabaseModule(private val appDatabase: AppDatabase) {
    @Provides
    internal fun providesAppDatabase(): AppDatabase{
        return appDatabase
    }
}
