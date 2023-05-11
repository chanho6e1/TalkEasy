package com.ssafy.talkeasy.feature.follow.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.talkeasy.feature.follow.ui.mobile.FollowListRoute

const val welcomeRouteProtector = "welcome_route_protector"
const val followListNavigationRoute = "follow_list_route"

fun NavController.navigateToFollowList(navOptions: NavOptions? = null) {
    this.navigate(followListNavigationRoute, navOptions)
}

fun NavGraphBuilder.followListScreen(
    navController: NavController,
) {
    composable(route = followListNavigationRoute) { navBackStackEntry ->
        val followListEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(welcomeRouteProtector)
        }
        FollowListRoute(navBackStackEntry = followListEntry)
    }
}