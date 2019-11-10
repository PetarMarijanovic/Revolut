package com.petarmarijanovic.revolut.core.usecase

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/** Caches created query by [Param] which has to override equals and hashcode. */
abstract class QueryUseCaseWithCachedParam<Param, Result> : QueryUseCaseWithParam<Param, Result> {

    private val flowableCache: MutableMap<Param, Flowable<Result>> = HashMap()

    final override fun invoke(param: Param): Flowable<Result> = getOrCreateFlowable(param)

    protected abstract fun createQuery(param: Param): Flowable<Result>

    @Synchronized
    private fun getOrCreateFlowable(param: Param): Flowable<Result> {
        flowableCache[param]?.let { return it }

        val flowable = createQuery(param)
                .doFinally { removeFromCache(param) }
                .replay(1)
                .refCount(1, TimeUnit.SECONDS, Schedulers.io())

        flowableCache[param] = flowable
        return flowable
    }

    @Synchronized
    private fun removeFromCache(key: Param) = flowableCache.remove(key)
}
