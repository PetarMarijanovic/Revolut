package com.petarmarijanovic.revolut.rateslib.repo

import com.petarmarijanovic.revolut.rateslib.model.Rate
import com.petarmarijanovic.revolut.rateslib.model.Rates
import com.petarmarijanovic.revolut.rateslib.service.RatesService
import io.reactivex.Single

class RatesRepoImpl(private val ratesService: RatesService) : RatesRepo {

    override fun fetchRates(base: Rate): Single<Rates> = ratesService.latest(base)

}
