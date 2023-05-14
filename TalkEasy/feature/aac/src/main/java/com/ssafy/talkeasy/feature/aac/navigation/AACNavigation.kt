package com.ssafy.talkeasy.feature.aac.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.talkeasy.feature.aac.ui.AACRouteFrame

const val AACRouteWard = "aac_route_ward"

fun NavController.navigateToAAC(navOptions: NavOptions? = null) {
    this.navigate(AACRouteWard, navOptions)
}

fun NavGraphBuilder.aacScreen() {
    composable(route = AACRouteWard) {
        AACRouteFrame()
    }
}