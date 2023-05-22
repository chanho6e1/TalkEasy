package com.ssafy.talkeasy.feature.chat.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ssafy.talkeasy.feature.chat.ui.mobile.ChatPartnerInfoScreen
import com.ssafy.talkeasy.feature.chat.ui.mobile.ChatRouteProtector

const val ChatRouteProtector = "chat_route_protector"
const val welcomeRouteProtector = "welcome_route_protector"
const val chatPartnerInfoProtector = "chat_partner_info_protector"

fun NavController.navigateToChat(navOptions: NavOptions? = null) {
    this.navigate(route = ChatRouteProtector, navOptions = navOptions)
}

fun NavController.navigateToFollowInfo(navOptions: NavOptions? = null) {
    this.navigate(route = chatPartnerInfoProtector, navOptions = navOptions)
}

fun NavGraphBuilder.chatScreen(navController: NavController) {
    composable(route = ChatRouteProtector) { navBackStackEntry ->
        val chatEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(welcomeRouteProtector)
        }
        ChatRouteProtector(
            navBackStackEntry = chatEntry,
            popBackStack = navController::popBackStack,
            onClickedInfoDetail = { navController.navigateToFollowInfo() }
        )
    }
}

fun NavGraphBuilder.chatPartnerScreen(navController: NavController) {
    composable(route = chatPartnerInfoProtector) { navBackStackEntry ->
        val chatPartnerInfoEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(welcomeRouteProtector)
        }

        ChatPartnerInfoScreen(
            popBackStack = navController::popBackStack,
            navBackStackEntry = chatPartnerInfoEntry
        )
    }
}