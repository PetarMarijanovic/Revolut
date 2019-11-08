package com.petarmarijanovic.revolut

import android.app.Application
import com.petarmarijanovic.revolut.core.appconfig.AppConfig
import com.petarmarijanovic.revolut.di.AppModule
import com.petarmarijanovic.revolut.navigation.di.NavigationModule
import com.petarmarijanovic.revolut.nextscreen.di.NextScreenModule
import com.petarmarijanovic.revolut.rates.di.RatesModule
import com.petarmarijanovic.revolut.rateslib.di.RatesLibModule
import com.petarmarijanovic.revolut.threading.di.ThreadingModule
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class RevolutApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RevolutApplication)
            modules(coreModules + libModules + featureModules)
        }

        get<List<AppConfig>>().forEach(AppConfig::configure)
    }

    private val coreModules: List<Module> = listOf(
        AppModule,
        ThreadingModule,
        NavigationModule
    )

    private val libModules: List<Module> = listOf(
        RatesLibModule
    )

    private val featureModules: List<Module> = listOf(
        RatesModule,
        NextScreenModule
    )
}