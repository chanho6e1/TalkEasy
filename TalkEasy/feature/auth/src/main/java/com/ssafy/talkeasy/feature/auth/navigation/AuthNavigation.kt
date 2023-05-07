package com.ssafy.talkeasy.feature.auth.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.talkeasy.feature.auth.ui.mobile.JoinRoute
import com.ssafy.talkeasy.feature.auth.ui.mobile.LoginRoute

const val loginNavigationRoute = "login_route"
const val joinNavigationRoute = "join_route"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(loginNavigationRoute, navOptions)
}

fun NavController.navigateToJoin(navOptions: NavOptions? = null) {
    this.navigate(joinNavigationRoute, navOptions)
}

fun NavGraphBuilder.loginScreen(
    onIsNotMember: () -> Unit,
    onIsLoginMember: () -> Unit,
) {
    composable(route = loginNavigationRoute) {
        LoginRoute(onIsNotMember = onIsNotMember, onIsLoginMember = onIsLoginMember)
    }
}

fun NavGraphBuilder.joinScreen(navController: NavController, onJoinMember: () -> Unit) {
    composable(route = joinNavigationRoute) { navBackStackEntry ->
        val loginEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(loginNavigationRoute)
        }
        JoinRoute(navBackStackEntry = loginEntry, onJoinMember = onJoinMember)
    }
}