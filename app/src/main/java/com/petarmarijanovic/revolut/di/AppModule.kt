package com.petarmarijanovic.revolut.di

import com.petarmarijanovic.revolut.appconfig.CrashlyticsAppConfig
import com.petarmarijanovic.revolut.appconfig.TimberAppConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val AppModule = module {

    single {
        listOf(
            TimberAppConfig(),
            CrashlyticsAppConfig(androidApplication())
        )
    }
}
