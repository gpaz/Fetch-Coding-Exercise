package com.algee.fetchexercise.startup

import android.content.Context
import androidx.startup.Initializer
import com.algee.fetchexercise.di.koin.modules.AppModule
import com.algee.fetchexercise.di.koin.modules.DomainModule
import com.algee.fetchexercise.di.koin.modules.intf.ModuleProvider
import com.algee.fetchexercise.di.koin.modules.RepositoryModule
import com.algee.fetchexercise.di.koin.modules.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

/**
 * Initializer that sets up and initializes the Koin dependency injection library.
 */
class KoinDependencyInjectionInitializer : Initializer<KoinApplication> {

    /**
     * A collection of [ModuleProvider]s established here right at the
     * top of the class as a property to make it easier to find and to
     * add or modify later on.
     *
     * Note: Add additional Koin module providers here.
     */
    private val mModuleProviders: List<ModuleProvider> = listOf(
        // App domain
        AppModule(),
        // Repositories
        RepositoryModule(),
        // Domain (i.e. use cases for MVVM Clean)
        DomainModule(),
        // ViewModels
        ViewModelModule()
    )

    override fun create(context: Context): KoinApplication =
        startKoin {
            androidLogger()
            androidContext(context) // This is the application context.
            modules((mModuleProviders.map { it.module }))

        }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
