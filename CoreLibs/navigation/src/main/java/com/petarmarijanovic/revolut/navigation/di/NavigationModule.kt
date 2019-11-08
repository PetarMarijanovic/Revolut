package com.petarmarijanovic.revolut.navigation.di

import com.petarmarijanovic.revolut.navigation.router.MainRouter
import com.petarmarijanovic.revolut.navigation.routing.RoutingActionsDispatcher
import com.petarmarijanovic.revolut.navigation.routing.RoutingActionsSource
import com.petarmarijanovic.revolut.navigation.routing.RoutingMediator
import com.petarmarijanovic.revolut.navigation.routing.RoutingMediatorImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MAIN_ROUTER_MEDIATOR = "Main router mediator"
const val MAIN_ROUTER_DISPATCHER = "Main router dispatcher"
const val MAIN_ROUTER_SOURCE = "Main router source"

val NavigationModule = module {

    single(named(MAIN_ROUTER_MEDIATOR)) { RoutingMediatorImpl<MainRouter>() as RoutingMediator<MainRouter> }
    single(named(MAIN_ROUTER_DISPATCHER)) { get<RoutingMediator<MainRouter>>(named(MAIN_ROUTER_MEDIATOR)) as RoutingActionsDispatcher<MainRouter> }
    single(named(MAIN_ROUTER_SOURCE)) { get<RoutingMediator<MainRouter>>(named(MAIN_ROUTER_MEDIATOR)) as RoutingActionsSource<MainRouter> }
}
