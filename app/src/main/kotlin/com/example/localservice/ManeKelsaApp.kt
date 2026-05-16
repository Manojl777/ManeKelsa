package com.example.localservice

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
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
    // ✅ KEY FIX: Use remember { mutableStateOf } - NOT rememberSaveable
    // This ensures recomposition happens when state changes
    var darkMode by remember { mutableStateOf(false) }
    var language by remember { mutableStateOf("en") }

    val navController = rememberNavController()
    val userRepository = remember { UserRepository() }

    // ✅ Theme wraps everything and recomposes when darkMode changes
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

            // ✅ LOGIN SCREEN
            composable("login") {
                LoginScreen(
                    language = language,
                    onLanguageChange = { newLanguage ->
                        language = newLanguage  // ✅ This triggers recomposition
                    },
                    darkMode = darkMode,
                    onDarkModeChange = { newDarkMode ->
                        darkMode = newDarkMode  // ✅ This triggers recomposition
                    },
                    userRepository = userRepository,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo("login") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            // ✅ ROLE SELECTION SCREEN
            composable("role_select") {
                RoleSelectionScreen(
                    language = language,
                    onLanguageChange = { newLanguage ->
                        language = newLanguage
                    },
                    darkMode = darkMode,
                    onDarkModeChange = { newDarkMode ->
                        darkMode = newDarkMode
                    },
                    userRepository = userRepository,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            // ✅ WORKER REGISTRATION SCREEN
            composable("register_worker") {
                WorkerRegistrationScreen(
                    language = language,
                    onLanguageChange = { newLanguage ->
                        language = newLanguage
                    },
                    darkMode = darkMode,
                    onDarkModeChange = { newDarkMode ->
                        darkMode = newDarkMode
                    },
                    userRepository = userRepository,
                    onNavigateWorkerDashboard = {
                        navController.navigate("home?role=worker") {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            // ✅ RESIDENT REGISTRATION SCREEN
            composable("register_resident") {
                ResidentRegistrationScreen(
                    language = language,
                    onLanguageChange = { newLanguage ->
                        language = newLanguage
                    },
                    darkMode = darkMode,
                    onDarkModeChange = { newDarkMode ->
                        darkMode = newDarkMode
                    },
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

            // ✅ HOME DASHBOARD SCREEN
            composable("home") {
                var dashboardRole by remember { mutableStateOf("resident") }
                LaunchedEffect(Unit) {
                    val u = Firebase.auth.currentUser ?: return@LaunchedEffect
                    dashboardRole = userRepository.getUserProfile(u.uid)?.role ?: "resident"
                }
                DashboardScaffold(
                    language = language,
                    onLanguageChange = { newLanguage ->
                        language = newLanguage
                    },
                    darkMode = darkMode,
                    onDarkModeChange = { newDarkMode ->
                        darkMode = newDarkMode
                    },
                    initialDashboardRole = dashboardRole,
                    userRepository = userRepository,
                    onAllServicesClick = {
                        // Handle all services click
                    }
                )
            }
        }
    }
}