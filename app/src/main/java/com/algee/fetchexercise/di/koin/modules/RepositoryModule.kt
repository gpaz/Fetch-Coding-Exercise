package com.algee.fetchexercise.di.koin.modules

import com.algee.fetchexercise.data.repository.HiringItemsRepository
import com.algee.fetchexercise.di.koin.modules.intf.ModuleProvider
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Module that creates and wires repositories.
 */
@JvmInline
internal value class RepositoryModule(
    override val module: Module = module {
        single {
            HiringItemsRepository(get())
        }
    }
) : ModuleProvider {
    operator fun invoke(): Module = module
}
