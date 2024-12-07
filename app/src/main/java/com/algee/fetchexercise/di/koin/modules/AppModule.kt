package com.algee.fetchexercise.di.koin.modules

import com.algee.fetchexercise.api.FetchApiFactory
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

internal object AppModule {
    private val mAppModule = module {
        single {
            FetchApiFactory()
        }
        single {
            val fetchApiFactory: FetchApiFactory = get()
            fetchApiFactory.build()
        }
        single(qualifier = qualifier(enum = DispatcherQualifier.Worker)) {
            Dispatchers.Default
        }
    }

    operator fun invoke(): Module = mAppModule

    enum class DispatcherQualifier {
        Worker
    }
}
