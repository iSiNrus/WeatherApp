package ru.barsik.weatherapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.barsik.weatherapp.App
import ru.barsik.weatherapp.di.component.ViewModelComponent

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun injectDependency(component: ViewModelComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createDaggerDependencies()
    }

    private fun createDaggerDependencies(){
        injectDependency((application as App).getViewModelComponent())
    }

}