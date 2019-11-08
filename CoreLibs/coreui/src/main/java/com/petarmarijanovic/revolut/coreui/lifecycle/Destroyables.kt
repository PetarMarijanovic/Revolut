package com.petarmarijanovic.revolut.coreui.lifecycle

class Destroyables : Destroyable {

    private val destroyableItems: MutableList<Destroyable> = mutableListOf()

    private var isDestroyed = false

    fun addDestroyable(destroyable: Destroyable) {
        if (isDestroyed) throw IllegalStateException("Cannot add the $destroyable to already destroyed Destroyables")
        destroyableItems.add(destroyable)
    }

    override fun destroy() {
        isDestroyed = true
        destroyableItems.asSequence().forEach(Destroyable::destroy)
    }
}
