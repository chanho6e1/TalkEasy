package com.ssafy.talkeasy.protector.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ssafy.talkeasy.feature.auth.navigation.joinScreen
import com.ssafy.talkeasy.feature.auth.navigation.loginNavigationRoute
import com.ssafy.talkeasy.feature.auth.navigation.loginScreen
import com.ssafy.talkeasy.feature.auth.navigation.navigateToJoin
import com.ssafy.talkeasy.feature.follow.navigation.followListScreen
import com.ssafy.talkeasy.feature.follow.navigation.navigateToFollowList

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = loginNavigationRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        loginScreen(
            onIsNotMember = {
                navController.navigateToJoin()
            },
            onIsLoginMember = {
                navController.navigateToFollowList()
            }
        )
        joinScreen(
            navController = navController,
            onJoinMember = {
                navController.navigateToFollowList()
            }
        )
        followListScreen()
    }
}