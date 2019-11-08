package com.petarmarijanovic.revolut

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.petarmarijanovic.revolut.navigation.di.MAIN_ROUTER_DISPATCHER
import com.petarmarijanovic.revolut.navigation.router.MainRouter
import com.petarmarijanovic.revolut.navigation.routing.RoutingActionConsumer
import com.petarmarijanovic.revolut.navigation.routing.RoutingActionsSource
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity(), RoutingActionConsumer<MainRouter> {

    private val routingActionsSource: RoutingActionsSource<MainRouter> by inject(named(MAIN_ROUTER_DISPATCHER))

    private val router: MainRouter by inject(parameters = { parametersOf(this) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) router.showRates()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        routingActionsSource.setActiveRoutingActionConsumer(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        routingActionsSource.unsetRoutingActionConsumer(this)
        super.onSaveInstanceState(outState)
    }

    override fun onRoutingAction(routingAction: (MainRouter) -> Unit) = routingAction(router)

    override fun onBackPressed() = router.goBack()
}
