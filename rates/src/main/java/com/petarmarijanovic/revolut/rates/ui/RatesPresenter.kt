package com.petarmarijanovic.revolut.rates.ui

import com.petarmarijanovic.revolut.coreui.base.BasePresenter
import com.petarmarijanovic.revolut.navigation.router.MainRouter
import com.petarmarijanovic.revolut.rates.model.RateWithValue
import com.petarmarijanovic.revolut.rates.model.RatesViewModel
import com.petarmarijanovic.revolut.rateslib.model.Rate
import com.petarmarijanovic.revolut.rateslib.usecase.QueryRefreshableRates
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers

class RatesPresenter(
    private val queryRefreshableRates: QueryRefreshableRates
) : BasePresenter<RatesContract.View, RatesViewState, MainRouter>(), RatesContract.Presenter {

    private val rateWithValuePublisher = PublishProcessor.create<RateWithValue>()
    private val rateWithValue =
        rateWithValuePublisher
            .startWith(RateWithValue(Rate.EUR, 100.0))
            .observeOn(Schedulers.io())

    override fun initialViewState(): RatesViewState = RatesViewState(emptyList())

    override fun onStart() {
        query(
            rateWithValue
                .switchMap {
                    queryRefreshableRates(it.rate)
                        .map { rates ->

                            { viewState: RatesViewState ->

                                val mutableRates = rates.rates.toMutableMap()

                                viewState.viewModels =

                                    mutableListOf<RatesViewModel>().apply {
                                        add(RatesViewModel(true, 0, it.rate, "name1", it.value))

                                        viewState.viewModels
                                            .forEach { viewModel ->
                                                if (viewModel.rate != it.rate) {
                                                    mutableRates.remove(viewModel.rate)
                                                        ?.let { value ->
                                                            add(
                                                                viewModel.copy(
                                                                    value = value * it.value,
                                                                    isSelected = false
                                                                )
                                                            )
                                                        }
                                                }
                                            }

                                        mutableRates.forEach { entry ->
                                            add(
                                                RatesViewModel(
                                                    false,
                                                    0,
                                                    entry.key,
                                                    "name1",
                                                    entry.value * it.value
                                                )
                                            )
                                        }
                                    }
                            }
                        }
                }
        )
    }

    override fun updateRate(rateWithValue: RateWithValue) =
        rateWithValuePublisher.onNext(rateWithValue)

    override fun openNextScreen() = dispatchRoutingAction(MainRouter::showNextScreen)
}
