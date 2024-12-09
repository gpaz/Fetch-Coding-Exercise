package com.algee.fetchexercise.di.koin.modules

import com.algee.fetchexercise.di.koin.modules.intf.ModuleProvider
import com.algee.fetchexercise.ui.section.hiring.DisplayHiringItemsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Module that creates and wires View Models.
 */
@JvmInline
internal value class ViewModelModule(
    override val module: Module = module {
        viewModelOf(::DisplayHiringItemsViewModel)
    }
) : ModuleProvider {
    operator fun invoke(): Module = module
}