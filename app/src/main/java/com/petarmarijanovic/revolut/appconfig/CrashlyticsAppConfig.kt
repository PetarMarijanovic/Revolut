package com.petarmarijanovic.revolut.appconfig

import android.content.Context
import com.crashlytics.android.Crashlytics
import com.petarmarijanovic.revolut.BuildConfig
import com.petarmarijanovic.revolut.core.appconfig.AppConfig
import io.fabric.sdk.android.Fabric

/** Configures Crashlytics for `release` and `staging` builds */
class CrashlyticsAppConfig(private val context: Context) : AppConfig {

    override fun configure() {
        when (BuildConfig.BUILD_TYPE) {
            "debug" -> Unit
            "staging", "release" -> Fabric.with(context, Crashlytics())
            else -> throw IllegalStateException("Crashlytics configuration not set for ${BuildConfig.BUILD_TYPE} build type")
        }
    }
}
