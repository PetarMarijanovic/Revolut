package com.petarmarijanovic.revolut.rates.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.petarmarijanovic.revolut.coreui.base.BaseFragment
import com.petarmarijanovic.revolut.rates.R
import com.petarmarijanovic.revolut.rates.adapter.RatesAdapter
import com.petarmarijanovic.revolut.rates.di.RATES_SCOPE
import kotlinx.android.synthetic.main.fragment_rates.*
import org.koin.core.parameter.parametersOf

class RatesFragment : BaseFragment<RatesViewState>(), RatesContract.View {

    companion object {
        @LayoutRes
        val LAYOUT_RESOURCE = R.layout.fragment_rates

        const val TAG = "RatesFragment"

        fun newInstance(): Fragment = RatesFragment()
    }

    private val presenter: RatesContract.Presenter by scopedInject()

    private val ratesAdapter: RatesAdapter by scopedInject(parameters = {
        parametersOf(
            LayoutInflater.from(context),
            presenter::updateRate
        )
    })

    override fun getLayoutResource(): Int = LAYOUT_RESOURCE
    override fun getScopeName(): String = RATES_SCOPE
    override fun getViewPresenter() = presenter

    override fun initialiseView(view: View, savedInstanceState: Bundle?) {
        with(recyclerview) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = ratesAdapter
        }

        next_screen.setOnClickListener { presenter.openNextScreen() }
    }

    override fun render(viewState: RatesViewState) = ratesAdapter.submitList(viewState.viewModels)
}
