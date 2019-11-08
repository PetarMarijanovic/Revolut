package com.petarmarijanovic.revolut.rates.di

import android.view.LayoutInflater
import com.petarmarijanovic.revolut.navigation.di.MAIN_ROUTER_DISPATCHER
import com.petarmarijanovic.revolut.rates.adapter.RatesAdapter
import com.petarmarijanovic.revolut.rates.model.OnRateClickListener
import com.petarmarijanovic.revolut.rates.ui.RatesContract
import com.petarmarijanovic.revolut.rates.ui.RatesPresenter
import com.petarmarijanovic.revolut.threading.di.BACKGROUND_SCHEDULER
import com.petarmarijanovic.revolut.threading.di.MAIN_SCHEDULER
import org.koin.core.qualifier.named
import org.koin.dsl.module

val RatesModule = module {

    scope(named(RATES_SCOPE)) {

        scoped<RatesContract.Presenter> {
            RatesPresenter(get()).apply {
                mainThreadScheduler = get(named(MAIN_SCHEDULER))
                backgroundScheduler = get(named(BACKGROUND_SCHEDULER))
                routingActionsDispatcher = get(named(MAIN_ROUTER_DISPATCHER))
                start()
            }
        }

        factory {
            val layoutInflater: LayoutInflater = it[0]
            val listener: OnRateClickListener = it[1]
            RatesAdapter(layoutInflater, listener)
        }
    }
}

const val RATES_SCOPE = "RATES_SCOPE"
