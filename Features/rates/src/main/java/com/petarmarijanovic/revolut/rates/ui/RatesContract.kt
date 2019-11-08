package com.petarmarijanovic.revolut.rates.ui

import com.petarmarijanovic.revolut.coreui.base.BaseView
import com.petarmarijanovic.revolut.coreui.base.ViewPresenter
import com.petarmarijanovic.revolut.rates.model.RateWithValue

interface RatesContract {

    interface View : BaseView

    interface Presenter : ViewPresenter<View, RatesViewState> {

        fun updateRate(rateWithValue: RateWithValue)

        fun openNextScreen()
    }
}
