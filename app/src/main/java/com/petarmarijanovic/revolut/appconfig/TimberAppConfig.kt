package com.petarmarijanovic.revolut.appconfig

import android.util.Log
import com.crashlytics.android.Crashlytics
import com.petarmarijanovic.revolut.BuildConfig
import com.petarmarijanovic.revolut.core.appconfig.AppConfig
import timber.log.Timber

/** Configures Timber on application level. Sets the [DebugTree] for `debug` and [CrashlyticsTree] for `release` and `stagings` builds */
class TimberAppConfig : AppConfig {

    override fun configure() {
        when (BuildConfig.BUILD_TYPE) {
            "debug" -> Timber.plant(DebugTree())
            "staging" -> Timber.plant(CrashlyticsTree(Log.INFO))
            "release" -> Timber.plant(CrashlyticsTree(Log.WARN))
            else -> throw IllegalStateException("Logging not set for ${BuildConfig.BUILD_TYPE} build type")
        }
    }
}

/**
 * Extends [Timber.DebugTree] but:
 * - Tag is a link to the log
 * - Throws exception if priority == [Log.ERROR]
 */
class DebugTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.ERROR) {
            throw throwable ?: RuntimeException(message)
        }

        super.log(priority, tag, message, throwable)
    }

    override fun createStackElementTag(element: StackTraceElement) =
        "(${element.fileName}:${element.lineNumber})#${element.methodName}"
}

/** Logs everything above [minPriorityLevel] to Crashlytics */
private class CrashlyticsTree(private val minPriorityLevel: Int) : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority < minPriorityLevel) {
            return
        }

        Crashlytics.log(priority, tag, message)
        throwable?.let(Crashlytics::logException)
    }
}
