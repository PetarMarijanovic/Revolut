package com.petarmarijanovic.revolut.rates.resources

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.petarmarijanovic.revolut.rates.R
import com.petarmarijanovic.revolut.rateslib.model.Rate
import com.petarmarijanovic.revolut.rateslib.model.Rate.*

@DrawableRes
fun getIconRes(rate: Rate): Int =
    when (rate) {
        EUR -> R.drawable.ic_european_union
        AUD -> R.drawable.ic_australia
        BGN -> R.drawable.ic_bulgaria
        BRL -> R.drawable.ic_brazil
        CAD -> R.drawable.ic_canada
        CHF -> R.drawable.ic_switzerland
        CNY -> R.drawable.ic_china
        CZK -> R.drawable.ic_czech_republic
        DKK -> R.drawable.ic_denmark
        GBP -> R.drawable.ic_united_kingdom
        HKD -> R.drawable.ic_hong_kong
        HRK -> R.drawable.ic_croatia
        HUF -> R.drawable.ic_hungary
        IDR -> R.drawable.ic_indonesia
        ILS -> R.drawable.ic_israel
        INR -> R.drawable.ic_india
        ISK -> R.drawable.ic_iceland
        JPY -> R.drawable.ic_japan
        KRW -> R.drawable.ic_south_korea
        MXN -> R.drawable.ic_mexico
        MYR -> R.drawable.ic_malaysia
        NOK -> R.drawable.ic_norway
        NZD -> R.drawable.ic_new_zealand
        PHP -> R.drawable.ic_philippines
        PLN -> R.drawable.ic_republic_of_poland
        RON -> R.drawable.ic_romania
        RUB -> R.drawable.ic_russia
        SEK -> R.drawable.ic_sweden
        SGD -> R.drawable.ic_singapore
        THB -> R.drawable.ic_thailand
        TRY -> R.drawable.ic_turkey
        USD -> R.drawable.ic_united_states_of_america
        ZAR -> R.drawable.ic_south_africa
    }

@StringRes
fun getStringRes(rate: Rate): Int =
    when (rate) {
        EUR -> R.string.ic_european_union
        AUD -> R.string.ic_australia
        BGN -> R.string.ic_bulgaria
        BRL -> R.string.ic_brazil
        CAD -> R.string.ic_canada
        CHF -> R.string.ic_switzerland
        CNY -> R.string.ic_china
        CZK -> R.string.ic_czech_republic
        DKK -> R.string.ic_denmark
        GBP -> R.string.ic_united_kingdom
        HKD -> R.string.ic_hong_kong
        HRK -> R.string.ic_croatia
        HUF -> R.string.ic_hungary
        IDR -> R.string.ic_indonesia
        ILS -> R.string.ic_israel
        INR -> R.string.ic_india
        ISK -> R.string.ic_iceland
        JPY -> R.string.ic_japan
        KRW -> R.string.ic_south_korea
        MXN -> R.string.ic_mexico
        MYR -> R.string.ic_malaysia
        NOK -> R.string.ic_norway
        NZD -> R.string.ic_new_zealand
        PHP -> R.string.ic_philippines
        PLN -> R.string.ic_republic_of_poland
        RON -> R.string.ic_romania
        RUB -> R.string.ic_russia
        SEK -> R.string.ic_sweden
        SGD -> R.string.ic_singapore
        THB -> R.string.ic_thailand
        TRY -> R.string.ic_turkey
        USD -> R.string.ic_united_states_of_america
        ZAR -> R.string.ic_south_africa
    }
