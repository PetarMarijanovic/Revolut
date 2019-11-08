package com.petarmarijanovic.revolut.navigation.routing

import timber.log.Timber
import java.util.*

class RoutingMediatorImpl<T : Router> : RoutingMediator<T> {

    private val QUEUE_LOCK = Any()
    private val routingActionQueue = LinkedList<QueuedAction<T>>()

    private var routingActionConsumer: RoutingActionConsumer<T>? = null

    override fun dispatch(routingAction: (T) -> Unit) = dispatchDistinct(null, routingAction)

    override fun dispatchDistinct(actionId: String?, routingAction: (T) -> Unit) {
        synchronized(QUEUE_LOCK) {
            routingActionConsumer?.onRoutingAction(routingAction) ?: queueAction(routingAction, actionId)
        }
    }

    private fun queueAction(routingAction: (T) -> Unit, actionId: String?) {
        actionId?.let { routingActionQueue.removeAll { queuedAction -> queuedAction.actionId == it } }
        routingActionQueue.add(QueuedAction(routingAction, actionId))
    }

    override fun setActiveRoutingActionConsumer(routingActionConsumer: RoutingActionConsumer<T>) {
        if (this.routingActionConsumer != null) {
            Timber.w("Setting action consumer before the previous one was unset")
        }

        synchronized(QUEUE_LOCK) {
            this.routingActionConsumer ?: flushRoutingActions(routingActionConsumer)
            this.routingActionConsumer = routingActionConsumer
        }
    }

    private fun flushRoutingActions(routingActionConsumer: RoutingActionConsumer<T>) {
        while (routingActionQueue.peek() != null) {
            routingActionConsumer.onRoutingAction(routingActionQueue.poll().routingAction)
        }
    }

    override fun unsetRoutingActionConsumer(routingActionConsumer: RoutingActionConsumer<T>) {
        if (this.routingActionConsumer !== routingActionConsumer) {
            Timber.w("Can't unset foreign consumer")
            return
        }
        this.routingActionConsumer = null
    }

    class QueuedAction<T>(val routingAction: (T) -> Unit, val actionId: String? = null)
}
