package com.petarmarijanovic.revolut.threading.scheduler

import android.os.Looper
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class OnRescheduleNotifyMainScheduler : Scheduler() {

    private val mainScheduler = AndroidSchedulers.from(Looper.getMainLooper(), true)

    override fun createWorker() = object : Worker() {

        private val worker = mainScheduler.createWorker()

        override fun schedule(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Timber.e(IllegalStateException("RxChain already on MainThread!"))
            }

            return worker.schedule(run, delay, unit)
        }

        override fun dispose() = worker.dispose()

        override fun isDisposed() = worker.isDisposed
    }
}
