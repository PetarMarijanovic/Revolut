package com.petarmarijanovic.revolut

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.petarmarijanovic.revolut.rateslib.model.Rate
import com.petarmarijanovic.revolut.rateslib.usecase.QueryRefreshableRates
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val repo: QueryRefreshableRates by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        repo(Rate.EUR)
            .subscribe({ Log.w("Petarr", it.toString()) }, { Log.e("Petarr", "error", it) })
    }
}
