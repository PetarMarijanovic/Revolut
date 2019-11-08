package com.petarmarijanovic.revolut.nextscreen.ui

import com.petarmarijanovic.revolut.coreui.base.BaseView
import com.petarmarijanovic.revolut.coreui.base.ViewPresenter

interface NextScreenContract {

    interface View : BaseView

    interface Presenter : ViewPresenter<View, NextScreenViewState>
}
