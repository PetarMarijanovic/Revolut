package com.petarmarijanovic.revolut.core.appconfig

/** Configurations that are run only once on Application start */
interface AppConfig {

    fun configure()
}
