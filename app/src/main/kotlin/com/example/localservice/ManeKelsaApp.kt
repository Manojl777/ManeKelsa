package com.example.localservice

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.localservice.firebase.FirebaseManager
import com.example.localservice.repository.ResidentRepository
import com.example.localservice.repository.WorkerRepository
import com.example.localservice.screens.LoginScreen
import com.example.localservice.screens.ResidentDashboard
import com.example.localservice.screens.ResidentRegistrationScreen
import com.example.localservice.screens.RoleSelectionScreen
import com.example.localservice.screens.WorkerDashboard
import com.example.localservice.screens.WorkerRegistrationScreen
import com.example.localservice.screens.AllServices
import com.example.localservice.screens.AllWorkers
import com.example.localservice.screens.ResMyAccountScreen
import com.example.localservice.screens.ResSettingsScreen
import com.example.localservice.screens.ResWorkerDetailScreen
import com.example.localservice.screens.ResWorkerScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.localservice.screens.EarningItem
import com.example.localservice.screens.EarningsHistory
import com.example.localservice.screens.WorkerIDScreen
import com.example.localservice.screens.WorkerAcntSettings
import com.google.firebase.Timestamp
@Composable
fun ManeKelsaApp(
    darkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    language: String,
    onLanguageChange: (String) -> Unit
) {

    val navController = rememberNavController()

    val earningsHistory = remember {
        mutableStateListOf<EarningItem>()
    }

    val residentRepository = remember {
        ResidentRepository()
    }

    val workerRepository = remember {
        WorkerRepository()
    }

    NavHost(
        navController = navController,
        startDestination = "bootstrap",
        modifier = Modifier.fillMaxSize()
    ) {

        // BOOTSTRAP

        composable("bootstrap") {

            LaunchedEffect(Unit) {

                try {

                    val user = FirebaseManager.auth.currentUser

                    val nextRoute = if (user == null) {

                        "login"

                    } else {

                        val isResident =
                            residentRepository.residentExists(user.uid)

                        val isWorker =
                            workerRepository.workerExists(user.uid)

                        when {

                            isResident -> "resident_dashboard"

                            isWorker -> "worker_dashboard"

                            else -> "role_select"
                        }
                    }

                    navController.navigate(nextRoute) {

                        popUpTo("bootstrap") {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }

                } catch (e: Exception) {

                    navController.navigate("login") {

                        popUpTo("bootstrap") {
                            inclusive = true
                        }
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator()
            }
        }

        // LOGIN SCREEN

        composable("login") {

            LoginScreen(
                language = language,
                onLanguageChange = onLanguageChange,
                darkTheme = darkTheme,
                onThemeChange = onThemeChange,
                onNavigate = { route ->

                    navController.navigate(route) {

                        launchSingleTop = true
                    }
                }
            )
        }

        // ROLE SELECTION

        composable("role_select") {

            RoleSelectionScreen(
                language = language,
                onLanguageChange = onLanguageChange,
                darkTheme = darkTheme,
                onThemeChange = onThemeChange,

                onResidentClick = {

                    navController.navigate(
                        "register_resident"
                    )
                },

                onWorkerClick = {

                    navController.navigate(
                        "register_worker"
                    )
                },

                onSwitchAccount = {

                    FirebaseManager.auth.signOut()

                    navController.navigate("login") {

                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // WORKER REGISTRATION

        composable("register_worker") {

            WorkerRegistrationScreen(
                language = language,
                onLanguageChange = onLanguageChange,
                darkMode = darkTheme,
                onDarkModeChange = onThemeChange,

                onNavigateWorkerDashboard = {

                    navController.navigate(
                        "worker_dashboard"
                    ) {

                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                },

                onBack = {

                    navController.popBackStack()
                }
            )
        }

        // RESIDENT REGISTRATION

        composable("register_resident") {

            ResidentRegistrationScreen(
                language = language,
                onLanguageChange = onLanguageChange,
                darkMode = darkTheme,
                onDarkModeChange = onThemeChange,

                onRegistrationComplete = {

                    navController.navigate(
                        "resident_dashboard"
                    ) {

                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                },

                onBack = {

                    navController.popBackStack()
                }
            )
        }

        // RESIDENT DASHBOARD

        composable("resident_dashboard") {

            ResidentDashboard(

                language = language,

                onLanguageChange = onLanguageChange,

                darkTheme = darkTheme,

                onThemeChange = onThemeChange,

                onServiceClick = { service ->

                    navController.navigate(
                        "res_worker_screen/$service"
                    )
                },

                onSeeAllClick = {

                    navController.navigate("all_services")
                },

                onWorkerClick = { workerId ->

                    navController.navigate(
                        "worker_detail/$workerId"
                    )
                },
                onAllWorkersClick = {

                    navController.navigate("all_workers")
                },

                onProfileClick = { action ->

                    when(action) {

                        "account" -> {

                            navController.navigate("my_account")
                        }

                        "settings" -> {

                            navController.navigate("settings")
                        }

                        "signout" -> {

                            FirebaseManager.auth.signOut()

                            navController.navigate("login"){

                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            )
        }
        composable(
            "res_worker_screen/{serviceName}"
        ) {

            val serviceName =
                it.arguments?.getString("serviceName") ?: ""

            val kannadaServiceName = when (serviceName) {

                "House Cleaning" -> "ಮನೆ ಸ್ವಚ್ಛಗೊಳಿಸುವಿಕೆ"

                "Deep Cleaning" -> "ಆಳವಾದ ಸ್ವಚ್ಛಗೊಳಿಸುವಿಕೆ"

                "Electrical Repair" -> "ಎಲೆಕ್ಟ್ರಿಕಲ್ ದುರಸ್ತಿ"

                "Electric Installation" -> "ಎಲೆಕ್ಟ್ರಿಕ್ ಇನ್ಸ್ಟಾಲೇಶನ್"

                "Plumbing" -> "ಪ್ಲಂಬಿಂಗ್"

                "Appliance Repair" -> "ಉಪಕರಣ ದುರಸ್ತಿ"

                "AC Service" -> "ಎಸಿ ಸೇವೆ"

                "Painting" -> "ಪೇಂಟಿಂಗ್"

                "Carpentry" -> "ಕಾರ್ಪೆಂಟ್ರಿ"

                "Gardening" -> "ತೋಟಗಾರಿಕೆ"

                "Vehicle Wash" -> "ವಾಹನ ಕ್ಲೀನಿಂಗ್"

                "Elderly Care" -> "ಹಿರಿಯರ ಆರೈಕೆ"

                "Child Care" -> "ಮಕ್ಕಳ ಆರೈಕೆ"

                "Cooking" -> "ಅಡುಗೆ"

                "Pet Care" -> "ಪೆಟ್ ಕೇರ್"

                else -> serviceName
            }

            ResWorkerScreen(

                serviceNameEnglish = serviceName,

                serviceNameKannada = kannadaServiceName,

                language = language,

                onLanguageChange = onLanguageChange,

                darkMode = darkTheme,

                onDarkModeToggle = {

                    onThemeChange(!darkTheme)
                },

                onBack = {

                    navController.popBackStack()
                },

                onWorkerClick = { workerId ->

                    navController.navigate(
                        "worker_detail/$workerId"
                    )
                }
            )
        }

        composable("all_services") {

            AllServices(

                language = language,

                onLanguageChange = onLanguageChange,

                onBack = {

                    navController.popBackStack()
                },

                onServiceClick = { service ->

                    navController.navigate(
                        "res_worker_screen/$service"
                    )
                }
            )
        }

        composable("all_workers") {

            AllWorkers(

                language = language,

                onLanguageChange = onLanguageChange,

                darkMode = darkTheme,

                onDarkModeToggle = {

                    onThemeChange(!darkTheme)
                },

                onBack = {

                    navController.popBackStack()
                },

                onWorkerClick = { workerId ->

                    navController.navigate(
                        "worker_detail/$workerId"
                    )
                }
            )
        }

        composable(
            "worker_detail/{workerId}"
        ) {

            val workerId =
                it.arguments?.getString("workerId") ?: ""

            ResWorkerDetailScreen(

                workerId = workerId,

                language = language,

                darkMode = darkTheme,

                onDarkModeToggle = {

                    onThemeChange(!darkTheme)
                },

                onBack = {

                    navController.popBackStack()
                }
            )
        }
        composable("earnings_history/{total}") {


            EarningsHistory(
                language = language,
                onLanguageChange = onLanguageChange,
                darkMode = darkTheme,
                onDarkModeChange = onThemeChange,

                totalEarnings =
                    it.arguments
                        ?.getString("total")
                        ?.toIntOrNull()
                        ?: 0,

                earningsList = earningsHistory,

                onBack = {

                    navController.popBackStack()
                }
            )
        }

        composable("worker_id") {

            val auth = FirebaseManager.auth

            val firestore = FirebaseManager.firestore

            val uid = auth.currentUser?.uid ?: ""

            var fullName by remember {
                mutableStateOf("")
            }

            var profilePic by remember {
                mutableStateOf("")
            }

            var phone by remember {
                mutableStateOf("")
            }

            var services by remember {
                mutableStateOf<List<String>>(emptyList())
            }

            var workAreas by remember {
                mutableStateOf<List<String>>(emptyList())
            }

            var workerId by remember {
                mutableStateOf("")
            }

            LaunchedEffect(Unit) {

                firestore
                    .collection("workers")
                    .document(uid)
                    .get()
                    .addOnSuccessListener { document ->

                        fullName =
                            document.getString("fullName") ?: ""

                        phone =
                            document.getString("contactNumber") ?: ""

                        profilePic =
                            document.getString("profilePic") ?: ""

                        services =
                            (document.get("serviceTypes") as? List<*>)
                                ?.map { it.toString() }
                                ?: emptyList()

                        workAreas =
                            document.getString("preferredAreasText")
                                ?.split(",")
                                ?.map { it.trim() }
                                ?: emptyList()

                        workerId =
                            document.getString("id")
                                ?: "MKW${uid.takeLast(6)}"
                    }
            }

            WorkerIDScreen(

                language = language,

                fullName = fullName,

                phone = phone,

                services = services,

                workAreas = workAreas,

                profileImage = profilePic,

                workerUniqueId = workerId,

                onEnterDashboard = {

                    navController.popBackStack()
                }
            )
        }

        composable("worker_settings") {

            WorkerAcntSettings(

                language = language,

                darkMode = darkTheme,

                onDarkModeChange = onThemeChange,

                onBack = {

                    navController.popBackStack()
                },

                onEditProfile = {

                    navController.navigate("register_worker")
                },

                onLogout = {

                    navController.navigate("login") {

                        popUpTo(0)
                    }
                },
            )
        }

        composable("my_account") {

            ResMyAccountScreen(

                language = language,

                onLanguageChange = onLanguageChange,

                darkMode = darkTheme,

                onDarkModeToggle = {

                    onThemeChange(!darkTheme)
                },

                onBack = {

                    navController.popBackStack()
                },
                onEditProfile = {

                    navController.navigate(
                        "register_resident"
                    )
                }
            )
        }

        composable("settings") {

            ResSettingsScreen(

                language = language,

                onLanguageChange = onLanguageChange,

                darkMode = darkTheme,

                onDarkModeToggle = {

                    onThemeChange(!darkTheme)
                },

                onBack = {

                    navController.popBackStack()
                },

                onAccountDeleted = {

                    navController.navigate("login") {

                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // WORKER DASHBOARD

        // WORKER DASHBOARD

        composable("worker_dashboard") {

            val auth = FirebaseManager.auth

            val firestore = FirebaseManager.firestore

            val uid = auth.currentUser?.uid ?: ""

            var workerName by remember {
                mutableStateOf("")
            }

            var workerArea by remember {
                mutableStateOf("")
            }

            var workerProfile by remember {
                mutableStateOf("")
            }

            var workerServices by remember {
                mutableStateOf<List<String>>(emptyList())
            }

            var totalEarnings by remember {
                mutableStateOf(0)
            }

            var isAvailable by remember {
                mutableStateOf(false)
            }

            var minimumRate by remember {
                mutableStateOf(0)
            }
            LaunchedEffect(Unit) {

                firestore
                    .collection("workers")
                    .document(uid)
                    .get()
                    .addOnSuccessListener { document ->

                        workerName =
                            document.getString("fullName") ?: ""

                        workerProfile =
                            document.getString("profilePic") ?: ""

                        workerArea =
                            document.getString("preferredAreasText")
                                ?.split(",")
                                ?.firstOrNull()
                                ?.trim()
                                ?: ""

                        workerServices =
                            (document.get("serviceTypes") as? List<*>)
                                ?.map { it.toString() }
                                ?: emptyList()

                        totalEarnings =
                            document.getLong("totalEarnings")
                                ?.toInt()
                                ?: 0
                        firestore
                            .collection("worker_status")
                            .document(uid)
                            .get()
                            .addOnSuccessListener { statusDoc ->

                                isAvailable =
                                    statusDoc.getBoolean("isAvailable")
                                        ?: false

                                minimumRate =
                                    statusDoc.getLong("minimumRate")
                                        ?.toInt()
                                        ?: 0
                            }

                        firestore
                            .collection("workers")
                            .document(uid)
                            .collection("earnings")
                            .get()
                            .addOnSuccessListener { result ->

                                earningsHistory.clear()

                                result.documents.forEach { earningDoc ->

                                    earningsHistory.add(

                                        EarningItem(

                                            amount =
                                                earningDoc
                                                    .getLong("amount")
                                                    ?.toInt()
                                                    ?: 0,

                                            date =
                                                earningDoc
                                                    .getString("date")
                                                    ?: "",

                                            time =
                                                earningDoc
                                                    .getString("time")
                                                    ?: ""
                                        )
                                    )
                                }
                            }
                    }
            }
            val workerRating = 4.5

            WorkerDashboard(

                language = language,

                darkTheme = darkTheme,

                workerName = workerName,

                workerArea = workerArea,

                workerServices = workerServices,

                workerProfile = workerProfile,

                totalEarnings = totalEarnings,

                workerRating = workerRating,



                onSaveSessionEarning = { amount ->

                    totalEarnings += amount

                    val currentDate =
                        java.text.SimpleDateFormat(
                            "dd MMM yyyy",
                            java.util.Locale.ENGLISH
                        ).format(java.util.Date())

                    val currentTime =
                        java.text.SimpleDateFormat(
                            "hh:mm a",
                            java.util.Locale.ENGLISH
                        ).format(java.util.Date())

                    firestore
                        .collection("workers")
                        .document(uid)
                        .collection("earnings")
                        .add(

                            hashMapOf(

                                "amount" to amount,

                                "date" to currentDate,

                                "time" to currentTime,

                                "createdAt" to Timestamp.now()
                            )
                        )

                    firestore
                        .collection("workers")
                        .document(uid)
                        .update(
                            "totalEarnings",
                            totalEarnings
                        )

                    earningsHistory.add(

                        0,

                        EarningItem(
                            amount = amount,
                            date = currentDate,
                            time = currentTime
                        )
                    )
                },

                onThemeChange = onThemeChange,

                onLanguageChange = onLanguageChange,

                onOpenAccount = {
                    navController.navigate("worker_id")
                },

                onOpenEarnings = {
                    navController.navigate(
                        "earnings_history/$totalEarnings"
                    )
                },

                onOpenSettings = {
                    navController.navigate("worker_settings")
                },

                onLogout = {

                    navController.navigate("login") {

                        popUpTo(0)
                    }
                },
                        workerId = uid,

                initialOnlineStatus = isAvailable,

                initialRate = minimumRate,

                onUpdateStatus = { available, rate ->

                    isAvailable = available

                    minimumRate = rate

                    FirebaseManager.firestore
                        .collection("worker_status")
                        .document(uid)
                        .set(

                            hashMapOf(

                                "workerId" to uid,

                                "isAvailable" to available,

                                "minimumRate" to rate
                            )
                        )
                }
            )

        }
    }
}