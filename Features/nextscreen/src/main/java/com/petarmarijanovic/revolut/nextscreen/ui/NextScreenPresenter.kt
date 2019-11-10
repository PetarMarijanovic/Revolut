package com.petarmarijanovic.revolut.nextscreen.ui

import com.petarmarijanovic.revolut.coreui.base.BasePresenter
import com.petarmarijanovic.revolut.navigation.router.MainRouter

class NextScreenPresenter : BasePresenter<NextScreenContract.View, NextScreenViewState, MainRouter>(), NextScreenContract.Presenter {

    override fun initialViewState(): NextScreenViewState = NextScreenViewState()
}
