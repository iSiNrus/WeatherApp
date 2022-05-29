package ru.barsik.weatherapp.di.component

import dagger.Component
import ru.barsik.weatherapp.di.module.ApiModule
import ru.barsik.weatherapp.di.scope.ApiScope
import ru.barsik.weatherapp.repo.server.ServerCommunicator

@ApiScope
@Component(modules = [ApiModule::class])
interface ApiComponent {
    val serverCommunicator: ServerCommunicator
}