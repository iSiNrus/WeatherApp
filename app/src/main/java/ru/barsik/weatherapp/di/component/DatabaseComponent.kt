package ru.barsik.weatherapp.di.component

import dagger.Component
import ru.barsik.weatherapp.di.module.DatabaseModule
import ru.barsik.weatherapp.repo.database.AppDatabase

@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    val database: AppDatabase
}