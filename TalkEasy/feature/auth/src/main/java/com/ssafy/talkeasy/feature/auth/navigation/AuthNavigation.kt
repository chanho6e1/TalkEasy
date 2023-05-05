package com.ssafy.talkeasy.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.talkeasy.feature.auth.JoinRoute
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
    onChangeIsMember: () -> Unit,
) {
    composable(route = loginNavigationRoute) {
        LoginRoute(onChangeIsMember = onChangeIsMember)
    }
}

fun NavGraphBuilder.joinScreen() {
    composable(route = joinNavigationRoute) {
        JoinRoute()
    }
}