package com.algee.fetchexercise.di.koin.modules

import com.algee.fetchexercise.data.domain.GetItemsUseCase
import com.algee.fetchexercise.di.koin.modules.intf.ModuleProvider
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Module that creates and wires domain use cases.
 */
@JvmInline
internal value class DomainModule(
    override val module: Module = module {
        factory {
            GetItemsUseCase(get())
        }
    }
) : ModuleProvider {
    operator fun invoke(): Module = module
}