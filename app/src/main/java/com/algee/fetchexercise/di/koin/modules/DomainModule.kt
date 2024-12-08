package com.algee.fetchexercise.di.koin.modules

import com.algee.fetchexercise.data.domain.GetItemsUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

internal object DomainModule {
    private val mDomainModule = module {
        factory {
            GetItemsUseCase(get())
        }
    }

    operator fun invoke(): Module = mDomainModule
}