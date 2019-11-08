package com.petarmarijanovic.revolut.navigation.routing

interface RoutingActionConsumer<T : Router> {

    fun onRoutingAction(routingAction: (T) -> Unit)
}