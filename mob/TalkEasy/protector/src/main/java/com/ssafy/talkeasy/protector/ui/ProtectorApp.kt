package com.ssafy.talkeasy.protector.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ssafy.talkeasy.protector.navigation.AppNavHost

@Composable
fun ProtectorApp() {
    Surface(modifier = Modifier.fillMaxSize()) {
        AppNavHost(navController = rememberNavController())
    }
}