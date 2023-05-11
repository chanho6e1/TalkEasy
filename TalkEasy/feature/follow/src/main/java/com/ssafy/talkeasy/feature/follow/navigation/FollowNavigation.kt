package com.ssafy.talkeasy.feature.follow.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.talkeasy.feature.follow.ui.mobile.AddFollowDetailRoute
import com.ssafy.talkeasy.feature.follow.ui.mobile.FollowListRoute

const val followListNavigationRoute = "follow_list_route"
const val addFollowDetailNavigationRoute = "add_follow_detail_route"

fun NavController.navigateToFollowList(navOptions: NavOptions? = null) {
    this.navigate(followListNavigationRoute, navOptions)
}

fun NavController.navigateToAddFollowDetail(navOptions: NavOptions? = null) {
    this.navigate(addFollowDetailNavigationRoute, navOptions)
}

fun NavGraphBuilder.followListScreen(navController: NavController) {
    composable(route = followListNavigationRoute) {
        FollowListRoute(onClickedAddFollow = { navController.navigateToAddFollowDetail() })
    }
}

fun NavGraphBuilder.addFollowDetailScreen() {
    composable(route = addFollowDetailNavigationRoute) {
        AddFollowDetailRoute()
    }
}