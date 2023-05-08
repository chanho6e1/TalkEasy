package com.ssafy.talkeasy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ssafy.talkeasy.feature.auth.navigation.joinScreen
import com.ssafy.talkeasy.feature.auth.navigation.loginRouteWard
import com.ssafy.talkeasy.feature.auth.navigation.loginScreen
import com.ssafy.talkeasy.feature.auth.navigation.navigateToJoin
import com.ssafy.talkeasy.feature.auth.navigation.navigateToWelcome
import com.ssafy.talkeasy.feature.auth.navigation.welcomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = loginRouteWard,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        loginScreen(
            onIsNotMember = {
                navController.navigateToJoin(role = 1)
            },
            onIsLoginMember = {
                navController.navigateToWelcome()
            },
            role = 1
        )
        joinScreen(
            navController = navController,
            onJoinMember = {
                navController.navigateToWelcome()
            },
            role = 1
        )
        welcomeScreen()
    }
}