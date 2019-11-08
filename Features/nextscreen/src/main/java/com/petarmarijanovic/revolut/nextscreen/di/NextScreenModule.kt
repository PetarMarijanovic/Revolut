package com.petarmarijanovic.revolut.nextscreen.di

import com.petarmarijanovic.revolut.navigation.di.MAIN_ROUTER_DISPATCHER
import com.petarmarijanovic.revolut.nextscreen.ui.NextScreenContract
import com.petarmarijanovic.revolut.nextscreen.ui.NextScreenPresenter
import com.petarmarijanovic.revolut.threading.di.BACKGROUND_SCHEDULER
import com.petarmarijanovic.revolut.threading.di.MAIN_SCHEDULER
import org.koin.core.qualifier.named
import org.koin.dsl.module

val NextScreenModule = module {

    scope(named(NEXT_SCREEN_SCOPE)) {

        scoped<NextScreenContract.Presenter> {
            NextScreenPresenter().apply {
                mainThreadScheduler = get(named(MAIN_SCHEDULER))
                backgroundScheduler = get(named(BACKGROUND_SCHEDULER))
                routingActionsDispatcher = get(named(MAIN_ROUTER_DISPATCHER))
                start()
            }
        }
    }
}

const val NEXT_SCREEN_SCOPE = "NEXT_SCREEN_SCOPE"
