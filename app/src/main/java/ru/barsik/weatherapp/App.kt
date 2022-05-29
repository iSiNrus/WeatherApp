package ru.barsik.weatherapp

import android.app.Application
import androidx.room.Room
import ru.barsik.weatherapp.di.component.*
import ru.barsik.weatherapp.di.module.ApiModule
import ru.barsik.weatherapp.di.module.DatabaseModule
import ru.barsik.weatherapp.di.module.ViewModelModule
import ru.barsik.weatherapp.repo.database.AppDatabase
import ru.barsik.weatherapp.di.module.RepositoryModule as RepositoryModule

class App : Application() {

    private var viewModelComponent: ViewModelComponent? = null
    private var database: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        initRoom()
        initDagger()
    }

    private fun initRoom(){
        database = Room.databaseBuilder(this, AppDatabase::class.java, "WeatherDB")
            .build()
    }

    private fun initDagger(){
        val apiComponent = DaggerApiComponent.builder()
            .apiModule(ApiModule())
            .build()

        val databaseComponent = DaggerDatabaseComponent.builder()
            .databaseModule(DatabaseModule(this.database!!))
            .build()

        val repositoryComponent = DaggerRepositoryComponent.builder()
            .apiComponent(apiComponent)
            .databaseComponent(databaseComponent)
            .repositoryModule(RepositoryModule())
            .build()

        viewModelComponent = DaggerViewModelComponent.builder()
            .repositoryComponent(repositoryComponent)
            .viewModelModule(ViewModelModule(this))
            .build()
    }

    fun getViewModelComponent(): ViewModelComponent {
        return this.viewModelComponent!!
    }
}