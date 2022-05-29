package ru.barsik.weatherapp.di.scope

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention
import javax.inject.Scope

@Scope
@Retention(RetentionPolicy.CLASS)
annotation class ApiScope()
