package com.petarmarijanovic.revolut.navigation.util

import androidx.fragment.app.FragmentManager
import com.petarmarijanovic.revolut.navigation.routing.BackPropagatingFragment

class ActivityUtilsImpl : ActivityUtils {

    /** @return `true` if back is consumed, `false` otherwise */
    override fun propagateBackToTopFragment(fragmentManager: FragmentManager): Boolean {
        return callIfPresent(findBackPropagatingFragment(fragmentManager), BackPropagatingFragment::back)
    }

    private fun callIfPresent(backPropagatingFragment: BackPropagatingFragment?, function: (BackPropagatingFragment) -> Boolean): Boolean {
        return backPropagatingFragment?.let(function::invoke) ?: false
    }

    private fun findBackPropagatingFragment(fragmentManager: FragmentManager): BackPropagatingFragment? =
            fragmentManager.fragments.lastOrNull() as? BackPropagatingFragment
}
