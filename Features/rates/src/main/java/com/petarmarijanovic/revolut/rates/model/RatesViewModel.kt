package com.petarmarijanovic.revolut.rates.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.petarmarijanovic.revolut.coreui.util.DiffUtilViewModel
import com.petarmarijanovic.revolut.rateslib.model.Rate

data class RatesViewModel(
    val isSelected: Boolean,
    @DrawableRes
    val icon: Int,
    val rate: Rate,
    @StringRes
    val name: Int,
    val value: Double
) : DiffUtilViewModel(rate)
