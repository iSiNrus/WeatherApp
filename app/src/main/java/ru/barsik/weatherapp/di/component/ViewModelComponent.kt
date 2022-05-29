package ru.barsik.weatherapp.di.component

import dagger.Component
import ru.barsik.weatherapp.di.module.ViewModelModule
import ru.barsik.weatherapp.di.scope.ViewModelScope
import ru.barsik.weatherapp.domain.AddLocationViewModel
import ru.barsik.weatherapp.presentation.AddLocationActivity
import ru.barsik.weatherapp.presentation.MainActivity

@ViewModelScope
@Component(
    modules = [ViewModelModule::class],
    dependencies = [RepositoryComponent::class]
)
interface ViewModelComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: AddLocationActivity)
}