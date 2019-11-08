package com.petarmarijanovic.revolut.navigation.routing

interface RoutingActionsSource<T : Router> {

    fun setActiveRoutingActionConsumer(routingActionConsumer: RoutingActionConsumer<T>)

    fun unsetRoutingActionConsumer(routingActionConsumer: RoutingActionConsumer<T>)
}