package com.petarmarijanovic.revolut.threading.di

import com.petarmarijanovic.revolut.threading.scheduler.OnRescheduleNotifyMainScheduler
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val BACKGROUND_SCHEDULER = "BACKGROUND_SCHEDULER"
const val MAIN_SCHEDULER = "MAIN_SCHEDULER"

val ThreadingModule = module {

    single(named(BACKGROUND_SCHEDULER)) { Schedulers.io() }

    single<Scheduler>(named(MAIN_SCHEDULER)) { OnRescheduleNotifyMainScheduler() }
}
