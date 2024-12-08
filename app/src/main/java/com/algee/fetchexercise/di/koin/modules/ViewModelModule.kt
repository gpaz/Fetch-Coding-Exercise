package com.algee.fetchexercise.di.koin.modules

import com.algee.fetchexercise.ui.section.hiring.DisplayHiringItemsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal object ViewModelModule {

    private val ViewModelModule = module {
        viewModelOf(::DisplayHiringItemsViewModel)
    }

    operator fun invoke(): Module = ViewModelModule

    enum class Qualifiers {
        HiringItemMap
    }
}