package com.ssafy.talkeasy.feature.location.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.talkeasy.feature.location.ui.mobile.LocationOpenRoute

const val locationOpenNavigationRoute = "location_open_route"

fun NavController.navigateToLocationOpen(navOptions: NavOptions? = null) {
    this.navigate(locationOpenNavigationRoute, navOptions)
}

fun NavGraphBuilder.locationOpenScreen() {
    composable(route = locationOpenNavigationRoute) {
        LocationOpenRoute()
    }
}