package com.example.toolbox.tools

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<DirectionsType : Any> : Fragment() {

    open val navController: NavController
        get() = findNavController()

    abstract val directions: DirectionsType

    fun navigateTo(
        directions: DirectionsType = this.directions,
        controller: NavController = this.navController,
        navOptions: NavOptions? = null,
        directionsMapper: (DirectionsType).() -> NavDirections,
    ) {
        controller.navigate(
            directions = directionsMapper(directions),
            navOptions = navOptions,
        )
    }

    fun popBackStack(): Boolean = navController.popBackStack()

    fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean): Boolean {
        return navController.popBackStack(destinationId, inclusive)
    }

    fun popBackStackToAnyOf(@IdRes vararg fragmentIds: Int) {
        fragmentIds.forEach { fragmentId ->
            if (navController.popBackStack(fragmentId, false)) {
                return@forEach
            }
        }
    }
}
