package com.petarmarijanovic.revolut.navigation.util

import android.os.Looper
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/** Must be called from main thread. */
fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    ensureMainThread()
    beginTransaction().func().commitNow()
}

/** Must be called from main thread. */
fun FragmentManager.inTransactionAndAddToBackStack(
    name: String? = null,
    func: FragmentTransaction.() -> FragmentTransaction
) {
    ensureMainThread()
    beginTransaction().func().addToBackStack(name).commit()
    executePendingTransactions()
}

private fun ensureMainThread() {
    if (Looper.getMainLooper().thread != Thread.currentThread()) {
        throw IllegalStateException("Fragment manager transactions must be called from main thread!")
    }
}
