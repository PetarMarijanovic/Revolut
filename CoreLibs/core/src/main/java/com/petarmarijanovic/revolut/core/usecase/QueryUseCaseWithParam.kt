package com.petarmarijanovic.revolut.core.usecase

import io.reactivex.Flowable

interface QueryUseCaseWithParam<Param, Result> {

    operator fun invoke(param: Param): Flowable<Result>
}
