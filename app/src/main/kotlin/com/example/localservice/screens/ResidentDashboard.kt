package com.example.localservice.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.LocalCarWash
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import androidx.compose.ui.draw.alpha
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.asImageBitmap

private val ColorPrimary = Color(0xFF006D67)
private val ColorBackground = Color(0xFFF8F6EF)

data class DashboardService(
    val english: String,
    val kannada: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

data class DashboardWorker(
    val workerId: String = "",
    val fullName: String = "",
    val servicesEnglish: List<String> = emptyList(),
    val servicesKannada: List<String> = emptyList(),
    val workerRatings: Double = 0.0,
    val minRate: String = "",
    val phone: String = "",
    val profileImage: String = "",
    val workerStatus: String = "offline"
)

@Composable
fun ResidentDashboard(

    language: String = "en",

    darkTheme: Boolean = false,

    onThemeChange: (Boolean) -> Unit = {},

    onLanguageChange: (String) -> Unit = {},

    onServiceClick: (String) -> Unit = {},

    onWorkerClick: (String) -> Unit = {},

    onSeeAllClick: () -> Unit = {},

    onProfileClick: (String) -> Unit = {},

    onAllWorkersClick: () -> Unit = {}

) {

    val firestore = FirebaseFirestore.getInstance()

    val currentUser = Firebase.auth.currentUser

    var userName by remember {
        mutableStateOf("")
    }

    var userArea by remember {
        mutableStateOf("")
    }

    val workerList = remember {
        mutableStateListOf<DashboardWorker>()
    }

    LaunchedEffect(Unit) {

        val uid = currentUser?.uid ?: return@LaunchedEffect

        firestore
            .collection("residents")
            .document(uid)
            .get()
            .addOnSuccessListener {

                userName =
                    it.getString("fullName") ?: ""

                userArea =
                    it.getString("area") ?: ""
            }

        firestore
            .collection("workers")
            .limit(5)
            .get()
            .addOnSuccessListener { result ->

                workerList.clear()

                result.documents.forEach { document ->

                    val workerId = document.id

                    firestore
                        .collection("worker_status")
                        .document(workerId)
                        .get()
                        .addOnSuccessListener { statusDoc ->

                            val isAvailable =
                                statusDoc.getBoolean("isAvailable")
                                    ?: false

                            val minimumRate =
                                (
                                        statusDoc.getLong("minimumRate")
                                            ?: 0L
                                        ).toString()
                            val serviceCodes =
                                document.get("serviceTypes")
                                        as? List<String>
                                    ?: emptyList()

                            val englishServices = mutableListOf<String>()

                            val kannadaServices = mutableListOf<String>()

                            serviceCodes.forEach { code ->

                                when(code.trim()) {

                                    "svcHouseCleaning" -> {

                                        englishServices.add("House Cleaning")
                                        kannadaServices.add("ಮನೆ ಸ್ವಚ್ಛಗೊಳಿಸುವಿಕೆ")
                                    }

                                    "svcDeepCleaning" -> {

                                        englishServices.add("Deep Cleaning")
                                        kannadaServices.add("ಡೀಪ್ ಕ್ಲೀನಿಂಗ್")
                                    }

                                    "svcElectricalRepair" -> {

                                        englishServices.add("Electrical Repair")
                                        kannadaServices.add("ಎಲೆಕ್ಟ್ರಿಕಲ್ ರಿಪೇರಿ")
                                    }

                                    "svcElectricInstallation" -> {

                                        englishServices.add("Electric Installation")
                                        kannadaServices.add("ಎಲೆಕ್ಟ್ರಿಕ್ ಇನ್‌ಸ್ಟಾಲೇಶನ್")
                                    }

                                    "svcPlumbing" -> {

                                        englishServices.add("Plumbing")
                                        kannadaServices.add("ಪ್ಲಂಬಿಂಗ್")
                                    }

                                    "svcApplianceRepair" -> {

                                        englishServices.add("Appliance Repair")
                                        kannadaServices.add("ಉಪಕರಣ ದುರಸ್ತಿ")
                                    }

                                    "svcACService" -> {

                                        englishServices.add("AC Service")
                                        kannadaServices.add("ಎಸಿ ಸೇವೆ")
                                    }

                                    "svcPainting" -> {

                                        englishServices.add("Painting")
                                        kannadaServices.add("ಪೇಂಟಿಂಗ್")
                                    }

                                    "svcCarpentry" -> {

                                        englishServices.add("Carpentry")
                                        kannadaServices.add("ಕಾರ್ಪೆಂಟ್ರಿ")
                                    }

                                    "svcGardening" -> {

                                        englishServices.add("Gardening")
                                        kannadaServices.add("ತೋಟಗಾರಿಕೆ")
                                    }

                                    "svcVehicleWash" -> {

                                        englishServices.add("Vehicle Wash")
                                        kannadaServices.add("ವಾಹನ ತೊಳೆಯುವುದು")
                                    }
                                }
                            }

                            workerList.add(

                                DashboardWorker(

                                    workerId = workerId,

                                    fullName =
                                        document.getString("fullName") ?: "",

                                    servicesEnglish = englishServices,

                                    servicesKannada = kannadaServices,

                                    workerRatings =
                                        document.getDouble("rating")
                                            ?: 0.0,

                                    minRate = minimumRate,

                                    phone =
                                        document.getString("contactNumber")
                                            ?: "",

                                    profileImage =
                                        document.getString("profilePic")
                                            ?: "",
                                    workerStatus =
                                        if (isAvailable)
                                            "online"
                                        else
                                            "offline"
                                )
                            )
                        }
                    workerList.sortWith(

                        compareByDescending<DashboardWorker> {

                            it.workerStatus.lowercase() == "online"
                        }
                    )
                }
            }
    }

    val services = listOf(

        DashboardService(
            "House Cleaning",
            "ಮನೆ ಸ್ವಚ್ಛಗೊಳಿಸುವಿಕೆ",
            Icons.Default.CleaningServices
        ),

        DashboardService(
            "Electrical Repair",
            "ಎಲೆಕ್ಟ್ರಿಕಲ್ ದುರಸ್ತಿ",
            Icons.Default.ElectricalServices
        ),

        DashboardService(
            "Gardening",
            "ತೋಟಗಾರಿಕೆ",
            Icons.Default.Park
        ),

        DashboardService(
            "Plumbing",
            "ಪ್ಲಂಬಿಂಗ್",
            Icons.Default.LocationOn
        ),

        DashboardService(
            "Vehicle Wash",
            "ವಾಹನ ಕ್ಲೀನಿಂಗ್",
            Icons.Default.LocalCarWash
        ),

        DashboardService(
            "All Services",
            "ಎಲ್ಲಾ ಸೇವೆಗಳು",
            Icons.Default.HomeRepairService
        )
    )

    LazyColumn(

        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)

    ) {

        item {

            TopSection(
                language = language,
                darkTheme = darkTheme,
                onThemeChange = onThemeChange,
                onLanguageChange = onLanguageChange,
                onProfileClick = onProfileClick
            )

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(

                    text = if (language == "en")
                        "Hello, $userName!"
                    else
                        "ಹಲೋ, $userName!",

                    fontSize = 28.sp,

                    fontWeight = FontWeight.ExtraBold,

                    color = Color(0xFF4A4A4A),

                    lineHeight = 30.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFFFF6A3D),
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = userArea,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = if (language == "en")
                        "For You"
                    else
                        "ನಿಮಗಾಗಿ",

                    color = ColorPrimary,

                    fontSize = 24.sp,

                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(18.dp))
            }
        }

        item {

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {

                services.chunked(3).forEach { rowServices ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        rowServices.forEach { service ->

                            Box(
                                modifier = Modifier.weight(1f)
                            ) {

                                DashboardServiceCard(

                                    title = if (language == "en")
                                        service.english
                                    else
                                        service.kannada,

                                    icon = service.icon,

                                    onClick = {

                                        if (service.english == "All Services") {

                                            onSeeAllClick()

                                        } else {

                                            onServiceClick(service.english)
                                        }
                                    }
                                )
                            }
                        }

                        if (rowServices.size < 3) {

                            repeat(3 - rowServices.size) {

                                Spacer(
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        item {

            Spacer(modifier = Modifier.height(30.dp))

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),

                horizontalArrangement = Arrangement.SpaceBetween,

                verticalAlignment = Alignment.CenterVertically

            ) {

                Text(

                    text = if (language == "en")
                        "Top Rated Nearby"
                    else
                        "ಅತ್ಯುತ್ತಮ ರೇಟ್ ಪಡೆದವರು",

                    color = ColorPrimary,

                    fontSize = 18.sp,

                    fontWeight = FontWeight.Bold
                )

                Text(

                    text = if (language == "en")
                        "SEE ALL"
                    else
                        "ಎಲ್ಲವನ್ನೂ ನೋಡಿ",

                    color = Color(0xFFFF6A3D),

                    fontSize = 15.sp,

                    fontWeight = FontWeight.Bold,

                    modifier = Modifier.clickable {
                        onAllWorkersClick()
                    }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        items(workerList) { worker ->

            DashboardWorkerCard(

                worker = worker,

                language = language,

                onWorkerClick = {

                    if (
                        worker.workerStatus.lowercase() == "online"
                    ) {

                        onWorkerClick(worker.workerId)
                    }
                }
            )
        }

        item {

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun TopSection(

    language: String,

    darkTheme: Boolean,

    onThemeChange: (Boolean) -> Unit,

    onLanguageChange: (String) -> Unit,

    onProfileClick: (String) -> Unit


) {
    var showMenu by remember {
        mutableStateOf(false)
    }

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                horizontal = 16.dp,
                vertical = 14.dp
            ),

        verticalAlignment = Alignment.CenterVertically

    ) {

        Surface(

            color = ColorPrimary,

            shape = RoundedCornerShape(14.dp),

            modifier = Modifier.size(52.dp)

        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    Icons.Default.HomeRepairService,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(

            text = "Mane\nKelsa",

            fontSize = 24.sp,

            lineHeight = 24.sp,

            fontWeight = FontWeight.ExtraBold,

            color = ColorPrimary,

            modifier = Modifier.weight(1f)
        )

        Surface(

            shape = CircleShape,

            color = Color(0xFFEAF2EE),

            modifier = Modifier.size(46.dp)

        ) {

            IconButton(
                onClick = {
                    onThemeChange(!darkTheme)
                }
            ) {

                Icon(

                    if (darkTheme)
                        Icons.Outlined.LightMode
                    else
                        Icons.Outlined.DarkMode,

                    contentDescription = null,

                    tint = ColorPrimary
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Surface(

            shape = RoundedCornerShape(30.dp),

            color = Color(0xFFEFF3F0),

            modifier = Modifier.clickable {

                onLanguageChange(
                    if (language == "en")
                        "kn"
                    else
                        "en"
                )
            }

        ) {

            Text(

                text = if (language == "en")
                    "ಕನ್ನಡ"
                else
                    "ENGLISH",

                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 10.dp
                ),

                color = ColorPrimary,

                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Box {

            Surface(

                shape = CircleShape,

                color = Color(0xFFFF5B1F),

                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        showMenu = true
                    }

            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Text(

                        text = "M",

                        color = Color.White,

                        fontWeight = FontWeight.Bold,

                        fontSize = 20.sp
                    )
                }
            }

            ResUserMenu(

                expanded = showMenu,

                language = language,

                onDismiss = {

                    showMenu = false
                },

                onMyAccount = {

                    showMenu = false

                    onProfileClick("account")
                },

                onSettings = {

                    showMenu = false

                    onProfileClick("settings")
                },

                onSignOut = {

                    showMenu = false

                    onProfileClick("signout")
                }
            )
        }
    }
}

@Composable
fun DashboardServiceCard(

    title: String,

    icon: androidx.compose.ui.graphics.vector.ImageVector,

    onClick: () -> Unit

) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )

    ) {

        Column(

            modifier = Modifier.fillMaxSize(),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center

        ) {

            Surface(

                shape = RoundedCornerShape(16.dp),

                color = Color(0xFFE8EEFF)

            ) {

                Icon(

                    icon,

                    contentDescription = null,

                    tint = ColorPrimary,

                    modifier = Modifier
                        .padding(12.dp)
                        .size(22.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(

                text = title,

                fontSize = 11.sp,

                lineHeight = 13.sp,

                fontWeight = FontWeight.Bold,

                color = Color.Gray
            )
        }
    }
}

@Composable
fun DashboardWorkerCard(

    worker: DashboardWorker,

    language: String,

    onWorkerClick: () -> Unit

) {

    val context = LocalContext.current

    val isOnline =
        worker.workerStatus.lowercase() == "online"

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 18.dp,
                vertical = 8.dp
            )
            .clickable(
                enabled = isOnline
            ) {

                onWorkerClick()
            },

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(

            containerColor =
                if (isOnline)
                    Color.White
                else
                    Color(0xFFF1F1F1)
        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .alpha(
                    if (isOnline) 1f else 0.5f
                ),

            verticalAlignment = Alignment.CenterVertically
        ){

            Box {

                val imageBytes = try {

                    Base64.decode(
                        worker.profileImage,
                        Base64.DEFAULT
                    )

                } catch (e: Exception) {

                    null
                }

                val bitmap = imageBytes?.let {

                    BitmapFactory.decodeByteArray(
                        it,
                        0,
                        it.size
                    )
                }

                if (bitmap != null) {

                    Image(

                        bitmap = bitmap.asImageBitmap(),

                        contentDescription = null,

                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape),

                        contentScale = ContentScale.Crop
                    )

                } else {

                    Icon(

                        Icons.Default.Person,

                        contentDescription = null,

                        tint = Color.Gray,

                        modifier = Modifier.size(72.dp)
                    )
                }

                Surface(

                    color = if (isOnline)
                        Color(0xFF10B84B)
                    else
                        Color(0xFF4A4A4A),

                    shape = RoundedCornerShape(20.dp),

                    modifier = Modifier
                        .align(Alignment.BottomCenter)

                ) {

                    Text(

                        text = if (isOnline) {

                            if (language == "en")
                                "ONLINE"
                            else
                                "ಆನ್‌ಲೈನ್"

                        } else {

                            if (language == "en")
                                "OFFLINE"
                            else
                                "ಆಫ್‌ಲೈನ್"
                        },

                        color = Color.White,

                        fontSize = 8.sp,

                        fontWeight = FontWeight.Bold,

                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 3.dp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(

                    text = worker.fullName,

                    fontWeight = FontWeight.Bold,

                    fontSize = 18.sp,

                    color = Color(0xFF4A4A4A)
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(

                    text = if (language == "en")
                        worker.servicesEnglish.joinToString(", ")
                    else
                        worker.servicesKannada.joinToString(", "),

                    fontSize = 13.sp,

                    color = Color.Gray,

                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(

                        text = worker.workerRatings.toString(),

                        fontWeight = FontWeight.Bold,

                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    repeat(5) {

                        Icon(

                            Icons.Default.ThumbUp,

                            contentDescription = null,

                            tint = Color(0xFF10B84B),

                            modifier = Modifier.size(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(

                        text = if (language == "en")
                            "Min Rate:\n₹${worker.minRate}"
                        else
                            "ಕನಿಷ್ಠ ದರ:\n₹${worker.minRate}",

                        fontWeight = FontWeight.Bold,

                        fontSize = 12.sp,

                        color = ColorPrimary
                    )
                }
            }

            Surface(

                shape = RoundedCornerShape(18.dp),

                color =
                    if (isOnline)
                        Color(0xFFEAF2EE)
                    else
                        Color(0xFFE0E0E0),

                modifier = Modifier
                    .size(52.dp)
                    .clickable(
                        enabled = isOnline
                    ) {

                        val intent = Intent(

                            Intent.ACTION_DIAL,

                            Uri.parse("tel:${worker.phone}")
                        )

                        context.startActivity(intent)
                    }
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Icon(

                        Icons.Default.Call,

                        contentDescription = null,

                        tint = ColorPrimary,

                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}