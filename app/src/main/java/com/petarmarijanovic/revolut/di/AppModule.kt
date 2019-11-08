package com.petarmarijanovic.revolut.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.petarmarijanovic.revolut.appconfig.CrashlyticsAppConfig
import com.petarmarijanovic.revolut.appconfig.TimberAppConfig
import com.petarmarijanovic.revolut.navigation.router.MainRouter
import com.petarmarijanovic.revolut.navigation.util.ActivityUtils
import com.petarmarijanovic.revolut.navigation.util.ActivityUtilsImpl
import com.petarmarijanovic.revolut.routing.MainRouterImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val AppModule = module {

    single {
        listOf(
            TimberAppConfig(),
            CrashlyticsAppConfig(androidApplication())
        )
    }

    single<ActivityUtils> { ActivityUtilsImpl() }

    factory<MainRouter> {
        val activity: AppCompatActivity = it[0]
        val fragmentManager: FragmentManager = activity.supportFragmentManager
        MainRouterImpl(fragmentManager, get())
    }
}
