package com.petarmarijanovic.revolut.rateslib.di

import com.google.gson.Gson
import com.petarmarijanovic.revolut.rateslib.repo.RatesRepo
import com.petarmarijanovic.revolut.rateslib.repo.RatesRepoImpl
import com.petarmarijanovic.revolut.rateslib.service.RatesService
import com.petarmarijanovic.revolut.rateslib.usecase.QueryRefreshableRates
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val RatesLibModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://revolut.duckdns.org/")
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    single<RatesService> { get<Retrofit>().create(RatesService::class.java) }

    single<RatesRepo> { RatesRepoImpl(get()) }

    single { Gson() }

    single { QueryRefreshableRates(get()) }
}