package com.algee.fetchexercise.di.koin.modules

import com.algee.fetchexercise.api.FetchApi
import com.algee.fetchexercise.api.FetchApiFactory
import com.algee.fetchexercise.api.FetchMoshi
import com.algee.fetchexercise.data.repository.HiringItemsRepository
import com.algee.fetchexercise.di.koin.modules.intf.ModuleProvider
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

/**
 * App-wide modules containing building blocks for most other components in the app.
 * Excludes any UI-, Domain-, Repository-, or  ViewModel- related wiring but does include
 * things that will need to present to inject into them when they are created manually ot
 * through other Koin modules.
 */
@JvmInline
internal value class AppModule(
    override val module: Module = module {
        single<Moshi> {
            FetchMoshi()()
        }
        single<FetchApi> {
            FetchApiFactory(mMoshi = get()).build()
        }
        single(qualifier = qualifier(enum = DispatcherQualifier.WorkerDispatcher)) {
            Dispatchers.Default
        }
        single {
            HiringItemsRepository(get())
        }
    }
) : ModuleProvider {
    operator fun invoke(): Module = module

    enum class DispatcherQualifier {
        WorkerDispatcher
    }
}
