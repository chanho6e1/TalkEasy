package com.ssafy.talkeasy.feature.auth.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.talkeasy.feature.auth.ui.mobile.JoinRouteProtector
import com.ssafy.talkeasy.feature.auth.ui.mobile.LoginRouteProtector
import com.ssafy.talkeasy.feature.auth.ui.mobile.WelcomeRouteProtector
import com.ssafy.talkeasy.feature.auth.ui.tablet.JoinRouteWard
import com.ssafy.talkeasy.feature.auth.ui.tablet.LoginRouteWard
import com.ssafy.talkeasy.feature.auth.ui.tablet.WelcomeRouteWard

const val loginRouteProtector = "login_route_protector"
const val joinRouteProtector = "join_route_protector"
const val loginRouteWard = "login_route_ward"
const val joinRouteWard = "join_route_ward"
const val welcomeRouteWard = "welcome_route_ward"
const val welcomeRouteProtector = "welcome_route_protector"

fun NavController.navigateToLogin(navOptions: NavOptions? = null, role: Int) {
    if (role == 0) {
        this.navigate(loginRouteProtector, navOptions)
    } else {
        this.navigate(loginRouteWard, navOptions)
    }
}

fun NavController.navigateToJoin(navOptions: NavOptions? = null, role: Int) {
    if (role == 0) {
        this.navigate(joinRouteProtector, navOptions)
    } else {
        this.navigate(joinRouteWard, navOptions)
    }
}

fun NavController.navigateToWelcome(navOptions: NavOptions? = null, role: Int) {
    if (role == 0) {
        this.navigate(welcomeRouteProtector, navOptions)
    } else {
        this.navigate(welcomeRouteWard, navOptions)
    }
}

fun NavGraphBuilder.loginScreen(
    onIsNotMember: () -> Unit,
    onIsLoginMember: () -> Unit,
    role: Int,
) {
    if (role == 0) {
        composable(route = loginRouteProtector) {
            LoginRouteProtector(
                onIsNotMember = onIsNotMember,
                onIsLoginMember = onIsLoginMember,
                role = role
            )
        }
    } else {
        composable(route = loginRouteWard) {
            LoginRouteWard(
                onIsNotMember = onIsNotMember,
                onIsLoginMember = onIsLoginMember,
                role = role
            )
        }
    }
}

fun NavGraphBuilder.joinScreen(navController: NavController, onJoinMember: () -> Unit, role: Int) {
    if (role == 0) {
        composable(route = joinRouteProtector) { navBackStackEntry ->
            val loginEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry(loginRouteProtector)
            }
            JoinRouteProtector(navBackStackEntry = loginEntry, onJoinMember = onJoinMember)
        }
    } else {
        composable(route = joinRouteWard) { navBackStackEntry ->
            val loginEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry(loginRouteWard)
            }
            JoinRouteWard(navBackStackEntry = loginEntry, onJoinMember = onJoinMember)
        }
    }
}

fun NavGraphBuilder.welcomeScreen(
    navController: NavController,
    onFinishedLoading: () -> Unit,
    role: Int,
) {
    if (role == 0) {
        composable(route = welcomeRouteProtector) { navBackStackEntry ->
            val loginEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry(loginRouteProtector)
            }
            WelcomeRouteProtector(
                navBackStackEntry = loginEntry,
                onFinishedLoading = onFinishedLoading
            )
        }
    } else {
        composable(route = welcomeRouteWard) { navBackStackEntry ->
            val loginEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry(loginRouteWard)
            }
            WelcomeRouteWard(navBackStackEntry = loginEntry, onFinishedLoading = onFinishedLoading)
        }
    }
}