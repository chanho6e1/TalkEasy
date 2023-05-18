package com.ssafy.talkeasy.feature.follow.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.talkeasy.feature.follow.ui.mobile.AddFollowDetailRoute
import com.ssafy.talkeasy.feature.follow.ui.mobile.FollowListRoute
import com.ssafy.talkeasy.feature.follow.ui.mobile.MyNotificationListRoute

const val welcomeRouteProtector = "welcome_route_protector"
const val followListNavigationRoute = "follow_list_route"
const val addFollowDetailNavigationRoute = "add_follow_detail_route"
const val myNotificationListNavigationRoute = "my_notification_list_route"

fun NavController.navigateToFollowList(navOptions: NavOptions? = null) {
    this.navigate(followListNavigationRoute, navOptions)
}

fun NavController.navigateToAddFollowDetail(navOptions: NavOptions? = null) {
    this.navigate(addFollowDetailNavigationRoute, navOptions)
}

fun NavController.navigateToMyNotificationList(navOptions: NavOptions? = null) {
    this.navigate(myNotificationListNavigationRoute, navOptions)
}

fun NavGraphBuilder.followListScreen(
    navController: NavController,
    onClickedAddFollow: () -> Unit,
    onClickedNotification: () -> Unit,
    onClickedSettings: () -> Unit,
) {
    composable(route = followListNavigationRoute) { navBackStackEntry ->
        val followListEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(welcomeRouteProtector)
        }
        FollowListRoute(
            navBackStackEntry = followListEntry,
            onClickedAddFollow = onClickedAddFollow,
            onClickedNotification = onClickedNotification,
            onClickedSettings = onClickedSettings
        )
    }
}

fun NavGraphBuilder.addFollowDetailScreen(navController: NavController) {
    composable(route = addFollowDetailNavigationRoute) { navBackStackEntry ->
        val addFollowDetailEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(welcomeRouteProtector)
        }
        AddFollowDetailRoute(
            navBackStackEntry = addFollowDetailEntry,
            popBackStack = { navController.popBackStack() }
        )
    }
}

fun NavGraphBuilder.myNotificationListScreen() {
    composable(route = myNotificationListNavigationRoute) {
        MyNotificationListRoute()
    }
}