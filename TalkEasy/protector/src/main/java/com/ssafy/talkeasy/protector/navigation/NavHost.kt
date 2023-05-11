package com.ssafy.talkeasy.protector.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ssafy.talkeasy.feature.auth.navigation.joinScreen
import com.ssafy.talkeasy.feature.auth.navigation.loginRouteProtector
import com.ssafy.talkeasy.feature.auth.navigation.loginScreen
import com.ssafy.talkeasy.feature.auth.navigation.navigateToJoin
import com.ssafy.talkeasy.feature.auth.navigation.navigateToWelcome
import com.ssafy.talkeasy.feature.auth.navigation.welcomeScreen
import com.ssafy.talkeasy.feature.follow.navigation.followListScreen
import com.ssafy.talkeasy.feature.follow.navigation.navigateToFollowList

private const val ROLE = 0

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = loginRouteProtector,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        loginScreen(
            onIsNotMember = {
                navController.navigateToJoin(role = ROLE)
            },
            onIsLoginMember = {
                navController.navigateToWelcome(role = ROLE)
            },
            role = ROLE
        )
        joinScreen(
            navController = navController,
            onJoinMember = {
                navController.navigateToWelcome(role = ROLE)
            },
            role = ROLE
        )
        welcomeScreen(
            navController = navController,
            onFinishedLoading = {
                navController.navigateToFollowList()
            },
            role = ROLE
        )
        followListScreen(navController = navController)
    }
}