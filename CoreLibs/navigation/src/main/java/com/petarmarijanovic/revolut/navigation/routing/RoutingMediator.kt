package com.petarmarijanovic.revolut.navigation.routing

interface RoutingMediator<T : Router> : RoutingActionsDispatcher<T>, RoutingActionsSource<T>