package com.petarmarijanovic.revolut.rates.model

import com.petarmarijanovic.revolut.rateslib.model.Rate

data class RateWithValue(val rate: Rate, val value: Double)
