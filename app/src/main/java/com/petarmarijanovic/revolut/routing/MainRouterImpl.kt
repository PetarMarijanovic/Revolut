package com.petarmarijanovic.revolut.routing

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.petarmarijanovic.revolut.R
import com.petarmarijanovic.revolut.navigation.router.MainRouter
import com.petarmarijanovic.revolut.navigation.util.ActivityUtils
import com.petarmarijanovic.revolut.navigation.util.inTransactionAndAddToBackStack
import com.petarmarijanovic.revolut.nextscreen.ui.NextScreenFragment
import com.petarmarijanovic.revolut.rates.ui.RatesFragment

private const val LAST_FRAGMENT = 0

@IdRes
private const val MAIN_CONTAINER_ID = R.id.main_container

class MainRouterImpl(
    private val fragmentManager: FragmentManager,
    private val activityUtils: ActivityUtils
) : MainRouter {

    override fun showRates() {
        fragmentManager.inTransactionAndAddToBackStack(RatesFragment.TAG) {
            add(
                MAIN_CONTAINER_ID,
                RatesFragment.newInstance(),
                RatesFragment.TAG
            )
        }
    }

    override fun showNextScreen() {
        fragmentManager.inTransactionAndAddToBackStack(NextScreenFragment.TAG) {
            setCustomAnimations(
                R.anim.fragment_right_enter,
                R.anim.nothing,
                R.anim.nothing,
                R.anim.fragment_right_exit
            )
            add(
                MAIN_CONTAINER_ID,
                NextScreenFragment.newInstance(),
                NextScreenFragment.TAG
            )
        }
    }

    override fun goBack() {
        if (fragmentManager.backStackEntryCount != LAST_FRAGMENT) {
            if (!activityUtils.propagateBackToTopFragment(fragmentManager)) {
                fragmentManager.popBackStackImmediate()
            }
        }
    }
}
