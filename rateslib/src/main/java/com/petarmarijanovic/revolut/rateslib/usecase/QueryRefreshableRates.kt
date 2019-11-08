package com.petarmarijanovic.revolut.rateslib.usecase

import com.petarmarijanovic.revolut.core.usecase.QueryUseCaseWithCachedParam
import com.petarmarijanovic.revolut.rateslib.model.Rate
import com.petarmarijanovic.revolut.rateslib.model.Rates
import com.petarmarijanovic.revolut.rateslib.repo.RatesRepo
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private const val REFRESH_PERIOD_IN_SECONDS = 1L

class QueryRefreshableRates(private val repo: RatesRepo) : QueryUseCaseWithCachedParam<Rate, Rates>() {

    override fun createQuery(param: Rate): Flowable<Rates> =
        Flowable.interval(0, REFRESH_PERIOD_IN_SECONDS, TimeUnit.SECONDS, Schedulers.io())
            .flatMapSingle { repo.fetchRates(param) }
}
