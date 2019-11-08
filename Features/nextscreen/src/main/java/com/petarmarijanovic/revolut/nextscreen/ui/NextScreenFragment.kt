package com.petarmarijanovic.revolut.nextscreen.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.petarmarijanovic.revolut.coreui.base.BaseFragment
import com.petarmarijanovic.revolut.nextscreen.R
import com.petarmarijanovic.revolut.nextscreen.di.NEXT_SCREEN_SCOPE

class NextScreenFragment : BaseFragment<NextScreenViewState>(), NextScreenContract.View {

    companion object {

        @LayoutRes
        val LAYOUT_RESOURCE = R.layout.fragment_next_screen

        const val TAG = "NextScreenFragment"

        fun newInstance(): Fragment = NextScreenFragment()

    }

    private val presenter: NextScreenContract.Presenter by scopedInject()

    override fun getLayoutResource(): Int = LAYOUT_RESOURCE

    override fun getScopeName(): String = NEXT_SCREEN_SCOPE
    override fun getViewPresenter() = presenter

    override fun initialiseView(view: View, savedInstanceState: Bundle?) {
        view.setOnClickListener { presenter.back() }
    }

    override fun render(viewState: NextScreenViewState) = Unit
}
