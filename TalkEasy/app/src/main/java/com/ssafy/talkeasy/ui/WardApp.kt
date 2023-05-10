package com.ssafy.talkeasy.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ssafy.talkeasy.navigation.AppNavHost

@Composable
fun WardApp() {
    Surface(modifier = Modifier.fillMaxSize()) {
        AppNavHost(navController = rememberNavController())
    }
}