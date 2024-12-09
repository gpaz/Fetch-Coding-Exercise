package com.algee.fetchexercise.di.koin.modules.intf

import org.koin.core.module.Module

internal interface ModuleProvider {
    val module: Module
}