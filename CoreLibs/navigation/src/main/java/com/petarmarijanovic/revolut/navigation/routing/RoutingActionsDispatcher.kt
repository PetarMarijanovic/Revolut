package com.petarmarijanovic.revolut.navigation.routing

interface RoutingActionsDispatcher<T : Router> {

    fun dispatch(routingAction: (T) -> Unit)

    fun dispatchDistinct(actionId: String?, routingAction: (T) -> Unit)
}
