package com.petarmarijanovic.revolut.rates.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.petarmarijanovic.revolut.coreui.util.addOnSimpleTextChangedListener
import com.petarmarijanovic.revolut.rates.model.OnRateClickListener
import com.petarmarijanovic.revolut.rates.model.RateWithValue
import com.petarmarijanovic.revolut.rates.model.RatesViewModel
import kotlinx.android.synthetic.main.item_rate.view.*

class RateViewHolder(
        view: View,
        private val onRateClickListener: OnRateClickListener
) : RecyclerView.ViewHolder(view) {

    private var viewModel: RatesViewModel? = null

    init {
        itemView.value.addOnSimpleTextChangedListener { s ->
            viewModel?.let {
                if (it.isSelected) onRateClickListener(RateWithValue(it.rate, s?.toString()?.toDoubleOrNull() ?: 0.0))
            }
        }

        itemView.setOnClickListener { itemView.value.requestFocus() }

        itemView.value.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) viewModel?.let {
                onRateClickListener(RateWithValue(it.rate, it.value))
            }
        }
    }

    fun render(ratesViewModel: RatesViewModel) {
        viewModel = ratesViewModel

        with(itemView) {
            icon.setImageResource(ratesViewModel.icon)
            rate.text = ratesViewModel.rate.name
            name.setText(ratesViewModel.name)

            value.setText(toDecimalText(ratesViewModel))
        }

    }

    fun renderUpdate(ratesViewModel: RatesViewModel) {
        viewModel = ratesViewModel

        if (!ratesViewModel.isSelected) itemView.value.setText(toDecimalText(ratesViewModel))
    }

    private fun toDecimalText(ratesViewModel: RatesViewModel) =
            if (ratesViewModel.value > 0.0) String.format("%.2f", ratesViewModel.value) else ""
}