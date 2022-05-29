package ru.barsik.weatherapp.di.component

import dagger.Component
import ru.barsik.weatherapp.di.module.RepositoryModule
import ru.barsik.weatherapp.di.scope.RepositoryScope
import ru.barsik.weatherapp.repo.AppRepository

@RepositoryScope
@Component(modules = [RepositoryModule::class],
    dependencies = [ApiComponent::class, DatabaseComponent::class])
interface RepositoryComponent {
    val repository: AppRepository
}