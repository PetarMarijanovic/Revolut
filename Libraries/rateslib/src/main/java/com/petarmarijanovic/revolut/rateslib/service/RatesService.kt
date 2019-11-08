package com.petarmarijanovic.revolut.rateslib.service

import com.petarmarijanovic.revolut.rateslib.model.Rate
import com.petarmarijanovic.revolut.rateslib.model.Rates
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesService {

    @GET("latest")
    fun latest(@Query("base") base: Rate): Single<Rates>
}
