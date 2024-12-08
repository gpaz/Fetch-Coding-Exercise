package com.algee.fetchexercise

import android.app.Application
import com.algee.fetchexercise.di.koin.modules.AppModule
import com.algee.fetchexercise.di.koin.modules.ViewModelModule
import com.algee.fetchexercise.di.koin.modules.DomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FetchExerciseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupDependencyInjection()
    }

    private fun setupDependencyInjection() {
        startKoin {
            androidLogger()
            androidContext(this@FetchExerciseApplication)
            modules(
                // App domain
                AppModule(),
                // Domain (i.e. use cases for MVVM Clean)
                DomainModule(),
                //
                ViewModelModule()
            )
        }
    }
}