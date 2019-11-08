package com.petarmarijanovic.revolut.navigation.util

import androidx.fragment.app.FragmentManager

interface ActivityUtils {

    fun propagateBackToTopFragment(fragmentManager: FragmentManager): Boolean
}
