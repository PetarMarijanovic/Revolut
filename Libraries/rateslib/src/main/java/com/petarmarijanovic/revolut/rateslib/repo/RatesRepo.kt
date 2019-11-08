package com.petarmarijanovic.revolut.rateslib.repo

import com.petarmarijanovic.revolut.rateslib.model.Rate
import com.petarmarijanovic.revolut.rateslib.model.Rates
import io.reactivex.Single

interface RatesRepo {

    fun fetchRates(base: Rate): Single<Rates>

}
