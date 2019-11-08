package com.petarmarijanovic.revolut.rates.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.petarmarijanovic.revolut.coreui.util.PayloadDiffUtilCallback
import com.petarmarijanovic.revolut.rates.R
import com.petarmarijanovic.revolut.rates.model.OnRateClickListener
import com.petarmarijanovic.revolut.rates.model.RatesViewModel

private val LAYOUT = R.layout.item_rate

class RatesAdapter(
    private val layoutInflater: LayoutInflater,
    private val onRateClickListener: OnRateClickListener
) : ListAdapter<RatesViewModel, RateViewHolder>(PayloadDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder =
        RateViewHolder(
            layoutInflater.inflate(
                LAYOUT,
                parent,
                false
            ),
            onRateClickListener
        )

    override fun onBindViewHolder(holderRate: RateViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holderRate, position, payloads)
            return
        }

        payloads.filterIsInstance<Pair<RatesViewModel, RatesViewModel>>()
            .forEach { holderRate.renderUpdate(it.second) }
    }

    override fun onBindViewHolder(holderRate: RateViewHolder, position: Int) =
        holderRate.render(getItem(position))

}
