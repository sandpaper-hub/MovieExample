package com.practicum.movieexample.navigation

import androidx.fragment.app.Fragment

class NavigatorHolderImpl : NavigatorHolder {
    private var navigator: Navigator? = null
    override fun attachNavigator(navigator: Navigator) {
        this.navigator = navigator
    }

    override fun detachNavigator() {
        navigator = null
    }

    override fun openFragment(fragment: Fragment) {
        navigator?.openFragment(fragment)
    }
}