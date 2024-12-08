package com.algee.fetchexercise.di.koin.modules

import com.algee.fetchexercise.api.FetchApiFactory
import com.algee.fetchexercise.data.domain.GetItemsUseCase
import com.algee.fetchexercise.data.repository.HiringItemsRepository
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
        single {
            HiringItemsRepository(get())
        }
    }

    operator fun invoke(): Module = mAppModule

    enum class DispatcherQualifier {
        Worker
    }
}
