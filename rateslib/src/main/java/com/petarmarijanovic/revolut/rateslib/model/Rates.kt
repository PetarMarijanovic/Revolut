package com.petarmarijanovic.revolut.rateslib.model

data class Rates(
    val base: Rate,
    val rates: Map<Rate, Double>
)
