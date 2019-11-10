package com.petarmarijanovic.revolut.rates.ui

import com.petarmarijanovic.revolut.coreui.base.BasePresenter
import com.petarmarijanovic.revolut.navigation.router.MainRouter
import com.petarmarijanovic.revolut.rates.model.RateWithValue
import com.petarmarijanovic.revolut.rates.model.RatesViewModel
import com.petarmarijanovic.revolut.rates.resources.getIconRes
import com.petarmarijanovic.revolut.rates.resources.getStringRes
import com.petarmarijanovic.revolut.rateslib.model.Rate
import com.petarmarijanovic.revolut.rateslib.model.Rates
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
        query(rateWithValue.switchMap { queryRefreshableRates(it.rate).map { rates -> toViewStateAction(rates, it) } })
    }

    private fun toViewStateAction(rates: Rates, selected: RateWithValue): (RatesViewState) -> Unit =
        { viewState: RatesViewState ->
            val mutableRates = rates.rates.toMutableMap()

            viewState.viewModels =
                mutableListOf<RatesViewModel>().apply {

                    // Set the selected as the first one
                    add(RatesViewModel(true, getIconRes(selected.rate), selected.rate, getStringRes(selected.rate), selected.value))

                    // Iterate over old ViewModels and update values
                    viewState.viewModels
                        .forEach { viewModel ->
                            if (viewModel.rate != selected.rate) {
                                mutableRates.remove(viewModel.rate)
                                    ?.let { value -> add(viewModel.copy(value = value * selected.value, isSelected = false)) }
                            }
                        }

                    // Add any new ones to the end
                    mutableRates.forEach { entry ->
                        add(RatesViewModel(false, getIconRes(entry.key), entry.key, getStringRes(entry.key), entry.value * selected.value))
                    }
                }
        }

    override fun updateRate(rateWithValue: RateWithValue) = rateWithValuePublisher.onNext(rateWithValue)

    override fun openNextScreen() = dispatchRoutingAction(MainRouter::showNextScreen)
}
