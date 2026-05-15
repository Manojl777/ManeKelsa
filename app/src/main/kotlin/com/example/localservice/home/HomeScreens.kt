package com.example.localservice.home

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.localservice.data.UserProfile
import com.example.localservice.data.UserRepository
import com.example.localservice.i18n.t
import com.example.localservice.ui.ColorAppBg
import com.example.localservice.ui.ColorPrimary
import com.example.localservice.ui.ColorSecondary
import com.example.localservice.ui.ColorSurface
import com.example.localservice.ui.ColorTertiary
import kotlinx.coroutines.launch
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScaffold(
    language: String,
    onLanguageChange: (String) -> Unit,
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    initialDashboardRole: String,
    userRepository: UserRepository,
    onAllServicesClick: () -> Unit,
) {
    val navController = rememberNavController()
    var userRole by remember(initialDashboardRole) { mutableStateOf(initialDashboardRole) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    LaunchedEffect(initialDashboardRole) {
        userRole = initialDashboardRole
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val tr: (String) -> String = { t(language, it) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (currentRoute?.startsWith("worker_details") == true) {
                TopAppBar(
                    title = {
                        Text(
                            tr("workerDetails"),
                            fontWeight = FontWeight.Black,
                            color = ColorPrimary,
                            letterSpacing = (-0.5).sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = ColorTertiary.copy(alpha = 0.5f))
                        }
                    },
                    actions = {
                        IconButton(onClick = { onDarkModeChange(!darkMode) }) {
                            Surface(
                                shape = CircleShape,
                                border = androidx.compose.foundation.BorderStroke(1.dp, ColorTertiary.copy(alpha = 0.2f)),
                                color = ColorAppBg,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = if (darkMode) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                                        contentDescription = null,
                                        tint = ColorPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                        Surface(
                            onClick = { onLanguageChange(if (language == "en") "kn" else "en") },
                            shape = RoundedCornerShape(50),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ColorTertiary.copy(alpha = 0.2f)),
                            color = ColorAppBg,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                text = if (language == "en") tr("langEnglish") else tr("langKannada"),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = ColorPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = ColorAppBg)
                )
            } else {
                TopAppBar(
                    title = {
                        if (userRole == "worker") {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = ColorSecondary,
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.Work, contentDescription = null, tint = ColorSurface, modifier = Modifier.size(20.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    tr("appName") + " W",
                                    fontWeight = FontWeight.Black,
                                    color = ColorSecondary,
                                    letterSpacing = (-0.5).sp
                                )
                            }
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = ColorPrimary,
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.Home, contentDescription = null, tint = ColorSurface, modifier = Modifier.size(20.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    tr("appName"),
                                    fontWeight = FontWeight.Black,
                                    color = ColorPrimary,
                                    letterSpacing = (-0.5).sp
                                )
                            }
                        }
                    },
                actions = {
                    if (userRole == "worker") {
                        IconButton(onClick = { onDarkModeChange(!darkMode) }) {
                            Surface(
                                shape = CircleShape,
                                border = androidx.compose.foundation.BorderStroke(1.dp, ColorTertiary.copy(alpha = 0.2f)),
                                color = ColorAppBg,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = if (darkMode) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                                        contentDescription = null,
                                        tint = ColorPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                        Surface(
                            onClick = { onLanguageChange(if (language == "en") "kn" else "en") },
                            shape = RoundedCornerShape(50),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ColorTertiary.copy(alpha = 0.2f)),
                            color = ColorAppBg,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                text = if (language == "en") tr("langEnglish") else tr("langKannada"),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = ColorPrimary
                            )
                        }
                        Surface(
                            shape = CircleShape,
                            color = ColorTertiary.copy(alpha = 0.1f),
                            modifier = Modifier
                                .size(36.dp)
                                .clickable { userRole = "resident" },
                            border = androidx.compose.foundation.BorderStroke(1.dp, ColorSecondary.copy(alpha = 0.3f))
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.padding(6.dp))
                        }
                    } else {
                        IconButton(onClick = { onDarkModeChange(!darkMode) }) {
                            Surface(
                                shape = CircleShape,
                                border = androidx.compose.foundation.BorderStroke(1.dp, ColorTertiary.copy(alpha = 0.2f)),
                                color = ColorAppBg,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = if (darkMode) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                                        contentDescription = null,
                                        tint = ColorPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                        Surface(
                            onClick = { onLanguageChange(if (language == "en") "kn" else "en") },
                            shape = RoundedCornerShape(50),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ColorTertiary.copy(alpha = 0.2f)),
                            color = ColorAppBg,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                text = if (language == "en") tr("langEnglish") else tr("langKannada"),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = ColorPrimary
                            )
                        }
                        Box {

                            Surface(
                                shape = CircleShape,
                                color = ColorSecondary,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable {
                                        showProfileMenu = true
                                    },
                                border = BorderStroke(
                                    2.dp,
                                    ColorTertiary.copy(alpha = 0.12f)
                                )
                            ) {

                                Box(
                                    contentAlignment = Alignment.Center
                                ) {

                                    Text(
                                        "M",
                                        color = ColorSurface,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 16.sp
                                    )
                                }
                            }

                            DropdownMenu(
                                expanded = showProfileMenu,
                                onDismissRequest = {
                                    showProfileMenu = false
                                },
                                modifier = Modifier
                                    .background(ColorSurface)
                            ) {

                                DropdownMenuItem(
                                    text = {

                                        Text(
                                            "My Account",
                                            color = ColorTertiary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    },
                                    leadingIcon = {

                                        Icon(
                                            Icons.Default.AccountCircle,
                                            contentDescription = null,
                                            tint = ColorSecondary
                                        )
                                    },
                                    onClick = {

                                        showProfileMenu = false

                                        navController.navigate(
                                            "my_account"
                                        )
                                    }
                                )

                                DropdownMenuItem(
                                    text = {

                                        Text(
                                            "Settings",
                                            color = ColorTertiary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    },
                                    leadingIcon = {

                                        Icon(
                                            Icons.Default.Settings,
                                            contentDescription = null,
                                            tint = ColorSecondary
                                        )
                                    },
                                    onClick = {

                                        showProfileMenu = false

                                        navController.navigate(
                                            "settings"
                                        )
                                    }
                                )

                                HorizontalDivider(
                                    color = ColorTertiary.copy(alpha = 0.08f)
                                )

                                DropdownMenuItem(
                                    text = {

                                        Text(
                                            "Sign Out",
                                            color = Color.Red,
                                            fontWeight = FontWeight.Bold
                                        )
                                    },
                                    leadingIcon = {

                                        Icon(
                                            Icons.Default.Logout,
                                            contentDescription = null,
                                            tint = Color.Red
                                        )
                                    },
                                    onClick = {

                                        showProfileMenu = false

                                    }
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ColorAppBg)
            )
            }
        },
        bottomBar = {
            if (currentRoute?.startsWith("worker_details") != true) {
                NavigationBar(containerColor = ColorSurface) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text(tr("home"), fontSize = 10.sp) },
                    selected = true,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ColorPrimary,
                        selectedTextColor = ColorPrimary,
                        indicatorColor = ColorPrimary.copy(alpha = 0.1f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.History, contentDescription = null) },
                    label = { Text(tr("history"), fontSize = 10.sp) },
                    selected = false,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text(tr("profile"), fontSize = 10.sp) },
                    selected = false,
                    onClick = { }
                )
            }
        }},
        containerColor = ColorAppBg
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                AnimatedContent(
                    targetState = userRole,
                    transitionSpec = {
                        (
                            fadeIn(animationSpec = tween(220)) +
                                slideInHorizontally { it / 6 }
                            ) togetherWith (
                            fadeOut(animationSpec = tween(180)) +
                                slideOutHorizontally { -it / 6 }
                            )
                    },
                    label = "homeRole"
                ) { role ->
                    when (role) {
                        "resident" -> ResidentHomeScreen(
                            language = language,
                            userRepository = userRepository,
                            onWorkerClick = { workerId ->
                                navController.navigate("worker_details/$workerId")
                            },
                            onAllServicesClick = {
                                navController.navigate("all_services")
                            }
                        )
                        else -> WorkerHomeScreen(
                            language = language,
                            userRepository = userRepository,
                            onRegister = {
                                scope.launch {
                                    snackbarHostState.showSnackbar(tr("registerSnack"))
                                }
                            }
                        )
                    }
                }
            }
            composable("all_services") {

                AllServicesScreen(
                    language = language,
                    onServiceClick = { serviceName ->

                        navController.navigate(
                            "service_workers/$serviceName"
                        )
                    }
                )
            }
            composable(
                route = "service_workers/{serviceName}"
            ) { backStackEntry ->

                val serviceName =
                    backStackEntry.arguments?.getString("serviceName")
                        ?: ""

                ServiceWorkersScreen(
                    language = language,
                    serviceName = serviceName,
                    onWorkerClick = { workerId ->

                        navController.navigate(
                            "worker_details/$workerId"
                        )
                    }
                )
            }
            composable("worker_details/{workerId}") { backStackEntry ->
                val workerId = backStackEntry.arguments?.getString("workerId") ?: ""
                WorkerDetailsScreen(
                    workerId = workerId,
                    language = language,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun LivePulseDot(modifier: Modifier = Modifier) {
    val infinite = rememberInfiniteTransition(label = "livePulse")
    val pulseAlpha by infinite.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )
    Box(
        modifier = modifier
            .size(8.dp)
            .background(Color(0xFF2E7D32).copy(alpha = pulseAlpha), CircleShape)
    )
}

@Composable
fun ResidentHomeScreen(
    language: String,
    userRepository: UserRepository,
    onWorkerClick: (String) -> Unit,
    onAllServicesClick: () -> Unit
) {

    val tr: (String) -> String = { t(language, it) }

    val nearbyWorkers = remember {
        listOf(
            UserProfile(
                uid = "worker1",
                phone = "+919876543210",
                role = "worker",
                isOnline = true,
                fullName = "ನೀಲಾ ಹೆಗಡೆ",
                services = listOf("Child Care", "Elderly Care"),
                workAreas = "Rajajinagar",
                minRate = "480",
                ratingSum = 49,
                ratingCount = 10,
                totalEarnings = 8500
            ),
            UserProfile(
                uid = "worker2",
                phone = "+919876543211",
                role = "worker",
                isOnline = true,
                fullName = "ಸುರೇಶ್ ರೈನಾ",
                services = listOf("Electrical Repair", "Plumbing"),
                workAreas = "Yelahanka",
                minRate = "690",
                ratingSum = 46,
                ratingCount = 10,
                totalEarnings = 9200
            ),
            UserProfile(
                uid = "worker3",
                phone = "+919876543212",
                role = "worker",
                isOnline = true,
                fullName = "ಲತಾ ಮಂಗೇಶ್",
                services = listOf("Cooking", "Elderly Care"),
                workAreas = "Jayanagar",
                minRate = "890",
                ratingSum = 44,
                ratingCount = 10,
                totalEarnings = 12000
            ),
            UserProfile(
                uid = "worker4",
                phone = "+919876543213",
                role = "worker",
                isOnline = true,
                fullName = "ಮೀನಾಕುಮಾರ್",
                services = listOf("Appliance Repair"),
                workAreas = "Indiranagar",
                minRate = "900",
                ratingSum = 48,
                ratingCount = 10,
                totalEarnings = 11000
            )
        )
    }

    val services = listOf(
        ServiceItem("svcHouseCleaning", Icons.Default.CleaningServices, Color(0xFF3B82F6)),
        ServiceItem("svcElectricalRepair", Icons.Default.Bolt, Color(0xFFEAB308)),
        ServiceItem("svcDeepCleaning", Icons.Default.AutoAwesome, Color(0xFF22C55E)),
        ServiceItem("svcPlumbing", Icons.Default.WaterDrop, Color(0xFF06B6D4)),
        ServiceItem("svcACService", Icons.Default.ElectricBolt, Color(0xFF8B5CF6)),
        ServiceItem("allServices", Icons.Default.Work, Color(0xFFF97316))
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorAppBg)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {

            Spacer(modifier = Modifier.height(20.dp))

            Column {

                Text(
                    text = "ಹಲೋ, ಪರಿಕ್ಷಾ ಬಳಕೆದಾರ!",
                    style = MaterialTheme.typography.displaySmall,
                    color = ColorTertiary,
                    lineHeight = 38.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = ColorSecondary,
                        modifier = Modifier.size(15.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "ಬಿಜಯನಗರ, ಬೆಂಗಳೂರು",
                        style = MaterialTheme.typography.bodyMedium,
                        color = ColorTertiary.copy(alpha = 0.55f)
                    )
                }
            }
        }

        item {

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = tr("forYou"),
                style = MaterialTheme.typography.headlineLarge,
                color = ColorPrimary
            )
        }

        items(services.chunked(3)) { rowItems ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                rowItems.forEach { service ->

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(0.88f)
                            .clickable {

                                if (service.nameKey == "allServices") {
                                    onAllServicesClick()
                                }
                            },
                        shape = RoundedCornerShape(26.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = ColorSurface
                        ),
                        border = BorderStroke(
                            1.dp,
                            ColorTertiary.copy(alpha = 0.05f)
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        )
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = service.color.copy(alpha = 0.15f),
                                modifier = Modifier.size(54.dp)
                            ) {

                                Box(
                                    contentAlignment = Alignment.Center
                                ) {

                                    Icon(
                                        service.icon,
                                        contentDescription = null,
                                        tint = service.color,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = tr(service.nameKey),
                                style = MaterialTheme.typography.labelMedium,
                                color = ColorTertiary.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }

        item {

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = tr("topRated"),
                    style = MaterialTheme.typography.headlineLarge,
                    color = ColorPrimary,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = tr("seeAll"),
                    style = MaterialTheme.typography.labelLarge,
                    color = ColorSecondary
                )
            }
        }

        items(nearbyWorkers) { worker ->

            Card(
                onClick = {
                    onWorkerClick(worker.uid)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = ColorSurface
                ),
                border = BorderStroke(
                    1.dp,
                    ColorTertiary.copy(alpha = 0.05f)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {

                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier.size(68.dp)
                    ) {

                        Surface(
                            shape = RoundedCornerShape(22.dp),
                            color = ColorSecondary.copy(alpha = 0.08f),
                            modifier = Modifier.fillMaxSize()
                        ) {

                            Box(
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = worker.fullName?.first()?.toString() ?: "W",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = ColorSecondary
                                )
                            }
                        }

                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF22C55E),
                            modifier = Modifier
                                .size(22.dp)
                                .align(Alignment.BottomEnd)
                        ) {

                            Box(
                                contentAlignment = Alignment.Center
                            ) {

                                Icon(
                                    Icons.Default.ThumbUp,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = worker.fullName ?: "Worker",
                            style = MaterialTheme.typography.headlineSmall,
                            color = ColorTertiary
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = worker.services.joinToString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = ColorTertiary.copy(alpha = 0.5f)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "೪.೯",
                                style = MaterialTheme.typography.labelLarge,
                                color = ColorTertiary
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Row {

                                repeat(5) {

                                    Icon(
                                        Icons.Default.ThumbUp,
                                        contentDescription = null,
                                        tint = Color(0xFF22C55E),
                                        modifier = Modifier
                                            .size(12.dp)
                                            .padding(end = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "|",
                                color = ColorTertiary.copy(alpha = 0.2f)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {

                                Text(
                                    text = tr("minRateLabel"),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ColorTertiary.copy(alpha = 0.5f)
                                )

                                Text(
                                    text = "₹${worker.minRate}",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = ColorPrimary
                                )
                            }
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = ColorPrimary.copy(alpha = 0.08f),
                        modifier = Modifier.size(52.dp)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                Icons.Default.Phone,
                                contentDescription = null,
                                tint = ColorPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerHomeScreen(
    language: String,
    userRepository: UserRepository,
    onRegister: () -> Unit,
) {
    val tr: (String) -> String = { t(language, it) }
    val scope = rememberCoroutineScope()
    var userProfile by remember { mutableStateOf<UserProfile?>(null) }
    val uid: String? = "mock_worker_uid"

    var showMinRateDialog by remember { mutableStateOf(false) }
    var minRateInput by remember { mutableStateOf("") }
    
    var showEarningsDialog by remember { mutableStateOf(false) }
    var earningsInput by remember { mutableStateOf("") }

    LaunchedEffect(uid) {
        userProfile = UserProfile(
            uid = "mock_worker_uid",
            phone = "+919876543210",
            role = "worker",
            isOnline = true,
            fullName = "Rajesh K.",
            services = listOf("svcHouseCleaning"),
            workAreas = "Jayanagar",
            minRate = "399",
            ratingSum = 48,
            ratingCount = 10,
            totalEarnings = 12500
        )
    }

    if (showMinRateDialog) {
        AlertDialog(
            onDismissRequest = { showMinRateDialog = false },
            title = { Text(tr("setMinRateTitle")) },
            text = {
                OutlinedTextField(
                    value = minRateInput,
                    onValueChange = { minRateInput = it },
                    label = { Text(tr("minRateLabel")) }
                )
            },
            confirmButton = {
                Button(onClick = {
                    scope.launch {
                        uid?.let { userRepository.updateWorkerStatus(it, true, minRateInput) }
                    }
                    showMinRateDialog = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showMinRateDialog = false }) { Text(tr("cancel")) }
            }
        )
    }

    if (showEarningsDialog) {
        AlertDialog(
            onDismissRequest = { showEarningsDialog = false },
            title = { Text(tr("enterEarningsTitle")) },
            text = {
                OutlinedTextField(
                    value = earningsInput,
                    onValueChange = { earningsInput = it },
                    label = { Text(tr("earningsLabel")) }
                )
            },
            confirmButton = {
                Button(onClick = {
                    scope.launch {
                        val amount = earningsInput.toIntOrNull() ?: 0
                        uid?.let { 
                            userRepository.addEarnings(it, amount)
                            userRepository.updateWorkerStatus(it, false, null)
                        }
                    }
                    showEarningsDialog = false
                    earningsInput = ""
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { 
                    scope.launch {
                        uid?.let { userRepository.updateWorkerStatus(it, false, null) }
                    }
                    showEarningsDialog = false 
                }) { Text(tr("skip")) }
            }
        )
    }

    if (userProfile == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            androidx.compose.material3.CircularProgressIndicator()
        }
        return
    }

    val p = userProfile!!
    val isOnline = p.isOnline

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "${tr("goodAfternoon")}, ",
                fontSize = 26.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                fontWeight = FontWeight.Black,
                color = ColorTertiary,
                letterSpacing = (-0.5).sp
            )
            Text(
                text = p.fullName ?: "User",
                fontSize = 26.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                fontWeight = FontWeight.Black,
                color = ColorTertiary,
                letterSpacing = (-0.5).sp
            )
        }
        
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
            Text(
                text = (p.services.firstOrNull() ?: "SERVICE").uppercase(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = ColorSecondary,
                letterSpacing = 1.sp
            )
            Text(
                text = "  •  ${p.workAreas ?: "Area"}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTertiary.copy(alpha = 0.5f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = ColorSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, ColorTertiary.copy(alpha = 0.1f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(if (isOnline) Color(0xFF22C55E) else Color(0xFFEF4444), CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isOnline) tr("online") else tr("offline"),
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = ColorTertiary
                    )
                    Text(
                        text = if (isOnline) tr("acceptingBookings") else tr("notAcceptingBookings"),
                        fontSize = 11.sp,
                        color = ColorTertiary.copy(alpha = 0.5f),
                        lineHeight = 16.sp
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(tr("fixedRate"), fontSize = 10.sp, color = ColorTertiary.copy(alpha = 0.5f))
                    Text(
                        text = p.minRate ?: "NIL",
                        fontWeight = FontWeight.Black,
                        color = ColorPrimary
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                androidx.compose.material3.Switch(
                    checked = isOnline,
                    onCheckedChange = { checked ->
                        if (checked) {
                            showMinRateDialog = true
                        } else {
                            showEarningsDialog = true
                        }
                    },
                    colors = androidx.compose.material3.SwitchDefaults.colors(
                        checkedThumbColor = ColorSurface,
                        checkedTrackColor = ColorPrimary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(
                modifier = Modifier.weight(1f).height(110.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = ColorSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, ColorTertiary.copy(alpha = 0.1f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val formattedEarnings = java.text.NumberFormat.getNumberInstance(java.util.Locale("en", "IN")).format(p.totalEarnings)
                    Text(
                        text = "₹$formattedEarnings",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = ColorSecondary,
                        letterSpacing = (-1).sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tr("totalEarnings"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorTertiary.copy(alpha = 0.5f)
                    )
                }
            }

            Card(
                modifier = Modifier.weight(1f).height(110.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = ColorSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, ColorTertiary.copy(alpha = 0.1f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val rating = if (p.ratingCount > 0) String.format("%.1f", p.ratingSum.toFloat() / p.ratingCount) else "0.0"
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = rating,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = ColorPrimary,
                            letterSpacing = (-1).sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = ColorPrimary.copy(alpha = 0.1f),
                            modifier = Modifier.size(24.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.ThumbUp, contentDescription = null, tint = ColorPrimary, modifier = Modifier.size(14.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tr("yourRating"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorTertiary.copy(alpha = 0.5f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Surface(
            onClick = { },
            shape = RoundedCornerShape(24.dp),
            color = ColorSecondary.copy(alpha = 0.05f),
            modifier = Modifier.fillMaxWidth().height(64.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.History, contentDescription = null, tint = ColorSecondary, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(tr("earningHistory"), fontWeight = FontWeight.Black, color = ColorSecondary, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ServiceCard(name: String, icon: ImageVector, iconColor: Color, language: String) {
    val tr: (String) -> String = { t(language, it) }
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val cardElevation by animateDpAsState(
        targetValue = if (pressed) 6.dp else 2.dp,
        animationSpec = tween(durationMillis = 180),
        label = "serviceCardElevation"
    )
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = ColorSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        interactionSource = interactionSource
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                color = iconColor.copy(alpha = 0.1f),
                shape = CircleShape,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp), tint = iconColor)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = tr(name),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = ColorTertiary
            )
        }
    }
}

@Composable
fun HistoryCard(item: HistoryItem, language: String) {
    val tr: (String) -> String = { t(language, it) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = ColorSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = ColorPrimary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = ColorPrimary
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = tr(item.service), fontWeight = FontWeight.Bold, color = ColorTertiary)
                Text(text = item.provider, fontSize = 12.sp, color = ColorTertiary.copy(alpha = 0.6f))
            }
            Surface(
                color = ColorPrimary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = tr(item.status),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorPrimary,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

data class ServiceItem(val nameKey: String, val icon: ImageVector, val color: Color)
data class HistoryItem(val service: String, val provider: String, val status: String)

@Composable
fun WorkerDetailsScreen(
    workerId: String,
    language: String,
    onBack: () -> Unit
) {
    val tr: (String) -> String = { t(language, it) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorAppBg)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.size(120.dp)) {
                Surface(
                    shape = RoundedCornerShape(32.dp),
                    color = ColorSecondary.copy(alpha = 0.15f),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("M", fontSize = 48.sp, fontWeight = FontWeight.Black, color = ColorSecondary)
                    }
                }
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = ColorSurface,
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 8.dp, y = 8.dp)
                        .size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.ThumbUp, contentDescription = null, tint = Color(0xFF22C55E), modifier = Modifier.size(18.dp))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Meena Iyer",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = ColorPrimary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "4.9",
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp,
                    color = ColorTertiary.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Row {
                    repeat(4) {
                        Icon(Icons.Default.ThumbUp, contentDescription = null, tint = Color(0xFF22C55E), modifier = Modifier.size(14.dp).padding(end=2.dp))
                    }
                    Icon(Icons.Default.ThumbUp, contentDescription = null, tint = ColorTertiary.copy(alpha = 0.1f), modifier = Modifier.size(14.dp).padding(end=2.dp))
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Card(
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = ColorSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, ColorTertiary.copy(alpha = 0.05f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                // Row 1: Worker ID
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF06B6D4).copy(alpha = 0.1f),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = ColorPrimary, modifier = Modifier.size(20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(tr("workerId").uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ColorTertiary.copy(alpha = 0.4f), letterSpacing = 1.sp)
                        Text(workerId.ifEmpty { "W-8890" }, fontSize = 14.sp, fontWeight = FontWeight.Black, color = ColorPrimary, modifier = Modifier.padding(top = 2.dp))
                    }
                }
                
                HorizontalDivider(
                    color = ColorTertiary.copy(alpha = 0.05f),
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                
                // Row 2: Address
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = ColorSecondary.copy(alpha = 0.1f),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = ColorSecondary, modifier = Modifier.size(20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(tr("addressLabel"), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ColorTertiary.copy(alpha = 0.4f), letterSpacing = 1.sp)
                        Text("KHB Colony, 5th Block,\nKoramangala", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = ColorTertiary.copy(alpha = 0.8f), modifier = Modifier.padding(top = 2.dp))
                    }
                }
                
                HorizontalDivider(
                    color = ColorTertiary.copy(alpha = 0.05f),
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                
                // Row 3: Contact
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF3B82F6).copy(alpha = 0.1f),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF3B82F6), modifier = Modifier.size(20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(tr("contactLabel"), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ColorTertiary.copy(alpha = 0.4f), letterSpacing = 1.sp)
                        Text("+91 43210 98765", fontSize = 16.sp, fontWeight = FontWeight.Black, color = ColorTertiary.copy(alpha = 0.8f), modifier = Modifier.padding(top = 2.dp))
                    }
                }
                
                HorizontalDivider(
                    color = ColorTertiary.copy(alpha = 0.05f),
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                
                // Row 4: Skills
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFEAB308).copy(alpha = 0.15f),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.ElectricBolt, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(tr("skillsLabel"), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ColorTertiary.copy(alpha = 0.4f), letterSpacing = 1.sp)
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = ColorTertiary.copy(alpha = 0.05f),
                            modifier = Modifier.padding(top = 6.dp)
                        ) {
                            Text("DEEP CLEANING", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ColorTertiary.copy(alpha = 0.6f), modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        val context = LocalContext.current
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:+914321098765")
                }
                context.startActivity(intent)
            },
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary),
            modifier = Modifier.fillMaxWidth().height(64.dp)
        ) {
            Icon(Icons.Default.Phone, contentDescription = null, tint = ColorSurface)
            Spacer(modifier = Modifier.width(12.dp))
            Text(tr("callWorkerNow"), fontWeight = FontWeight.Bold, fontSize = 18.sp, color = ColorSurface)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}
