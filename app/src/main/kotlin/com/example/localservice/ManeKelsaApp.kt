package com.example.localservice

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.localservice.auth.LoginScreen
import com.example.localservice.auth.ResidentRegistrationScreen
import com.example.localservice.auth.RoleSelectionScreen
import com.example.localservice.auth.WorkerRegistrationScreen
import com.example.localservice.data.UserRepository
import com.example.localservice.home.DashboardScaffold
import com.example.localservice.ui.ManeKelsaTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ManeKelsaApp() {
    var darkMode by rememberSaveable { mutableStateOf(false) }
    var language by rememberSaveable { mutableStateOf("en") }
    val navController = rememberNavController()
    val userRepository = remember { UserRepository() }

    ManeKelsaTheme(darkTheme = darkMode) {
        NavHost(
            navController = navController,
            startDestination = "bootstrap",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("bootstrap") {
                LaunchedEffect(Unit) {
                    val user = Firebase.auth.currentUser
                    val next = when {
                        user == null -> "login"
                        else -> {
                            val p = userRepository.getUserProfile(user.uid)
                            when {
                                p?.profileComplete == true -> "home"
                                p?.role == "worker" && !p.profileComplete -> "register_worker"
                                p?.role == "resident" && !p.profileComplete -> "register_resident"
                                else -> "role_select"
                            }
                        }
                    }
                    navController.navigate(next) {
                        popUpTo("bootstrap") { inclusive = true }
                        launchSingleTop = true
                    }
                }
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            composable("login") {
                LoginScreen(
                    language = language,
                    onLanguageChange = { language = it },
                    darkMode = darkMode,
                    onDarkModeChange = { darkMode = it },
                    userRepository = userRepository,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo("login") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable("role_select") {
                RoleSelectionScreen(
                    language = language,
                    onLanguageChange = { language = it },
                    darkMode = darkMode,
                    onDarkModeChange = { darkMode = it },
                    userRepository = userRepository,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable("register_worker") {
                WorkerRegistrationScreen(
                    language = language,
                    onLanguageChange = { language = it },
                    darkMode = darkMode,
                    onDarkModeChange = { darkMode = it },
                    userRepository = userRepository,
                    onNavigateHome = {
                        navController.navigate("home") {
                            popUpTo(navController.graph.id) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("register_resident") {
                ResidentRegistrationScreen(
                    language = language,
                    onLanguageChange = { language = it },
                    darkMode = darkMode,
                    onDarkModeChange = { darkMode = it },
                    userRepository = userRepository,
                    onNavigateHome = {
                        navController.navigate("home") {
                            popUpTo(navController.graph.id) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("home") {
                var dashboardRole by remember { mutableStateOf("resident") }
                LaunchedEffect(Unit) {
                    val u = Firebase.auth.currentUser ?: return@LaunchedEffect
                    dashboardRole = userRepository.getUserProfile(u.uid)?.role ?: "resident"
                }
                DashboardScaffold(
                    language = language,
                    onLanguageChange = { language = it },
                    darkMode = darkMode,
                    onDarkModeChange = { darkMode = it },
                    initialDashboardRole = dashboardRole,
                    userRepository = userRepository,
                )
            }
        }
    }
}
