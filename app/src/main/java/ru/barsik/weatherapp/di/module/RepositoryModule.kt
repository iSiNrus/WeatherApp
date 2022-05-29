package ru.barsik.weatherapp.di.module

import dagger.Module
import dagger.Provides
import ru.barsik.weatherapp.di.scope.RepositoryScope
import ru.barsik.weatherapp.repo.AppRepository
import ru.barsik.weatherapp.repo.database.AppDatabase
import ru.barsik.weatherapp.repo.server.ServerCommunicator

@Module
class RepositoryModule {

    @RepositoryScope
    @Provides
    internal fun providesRepository(communicator: ServerCommunicator, database: AppDatabase) : AppRepository {
        return AppRepository(database, communicator)
    }

}