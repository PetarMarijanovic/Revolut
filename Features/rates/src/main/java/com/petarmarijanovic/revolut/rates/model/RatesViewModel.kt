package com.petarmarijanovic.revolut.rates.model

import com.petarmarijanovic.revolut.coreui.util.DiffUtilViewModel
import com.petarmarijanovic.revolut.rateslib.model.Rate

data class RatesViewModel(
    val isSelected: Boolean,
    val icon: Int,
    val rate: Rate,
    val name: String,
    val value: Double
) : DiffUtilViewModel(rate)
