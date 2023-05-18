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
import com.ssafy.talkeasy.feature.chat.navigation.chatPartnerScreen
import com.ssafy.talkeasy.feature.chat.navigation.chatScreen
import com.ssafy.talkeasy.feature.chat.navigation.navigateToChat
import com.ssafy.talkeasy.feature.follow.navigation.addFollowDetailScreen
import com.ssafy.talkeasy.feature.follow.navigation.followListScreen
import com.ssafy.talkeasy.feature.follow.navigation.myNotificationListScreen
import com.ssafy.talkeasy.feature.follow.navigation.navigateToAddFollowDetail
import com.ssafy.talkeasy.feature.follow.navigation.navigateToFollowList
import com.ssafy.talkeasy.feature.follow.navigation.navigateToMyNotificationList
import com.ssafy.talkeasy.feature.location.navigation.locationOpenScreen

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
            onFinishedLoading = { navController.navigateToFollowList() },
            role = ROLE
        )

        followListScreen(
            navController = navController,
            onClickedAddFollow = { navController.navigateToAddFollowDetail() },
            onClickedNotification = { navController.navigateToMyNotificationList() },
            onClickedSettings = { },
            onSelectedItem = { navController.navigateToChat() }
        )

        addFollowDetailScreen(navController = navController)

        myNotificationListScreen()

        locationOpenScreen()

        chatScreen(navController = navController)

        chatPartnerScreen(navController = navController)
    }
}