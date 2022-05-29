package ru.barsik.weatherapp.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import ru.barsik.weatherapp.App
import ru.barsik.weatherapp.di.scope.ViewModelScope
import ru.barsik.weatherapp.domain.AddLocationViewModel
import ru.barsik.weatherapp.domain.MainViewModel
import ru.barsik.weatherapp.repo.AppRepository

@Module
class ViewModelModule(app: App) {

    internal var app: Application = app

    @ViewModelScope
    @Provides
    internal fun providesAddTripViewModule(repository: AppRepository): AddLocationViewModel {
        return AddLocationViewModel(app, repository)
    }

    @ViewModelScope
    @Provides
    internal fun providesMainViewModule(repository: AppRepository): MainViewModel {
        return MainViewModel(app, repository)
    }
}