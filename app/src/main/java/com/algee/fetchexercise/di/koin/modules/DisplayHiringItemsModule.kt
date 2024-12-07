package com.algee.fetchexercise.di.koin.modules

import com.algee.fetchexercise.data.domain.GetItemsUseCase
import com.algee.fetchexercise.data.repository.HiringItemsRepository
import com.algee.fetchexercise.ui.section.hiring.DisplayHiringItemsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

object DisplayHiringItemsModule {
    private val mDisplayHiringItemsActivityModule = module {
        viewModelOf(::DisplayHiringItemsViewModel)
        scope<DisplayHiringItemsViewModel> {
            scoped {
                GetItemsUseCase(
                    get(clazz = HiringItemsRepository::class),
                    get(qualifier = qualifier(AppModule.DispatcherQualifier.Worker))
                )
            }
        }
    }

    operator fun invoke(): Module = mDisplayHiringItemsActivityModule

    enum class Qualifiers {
        HiringItemMap
    }
}