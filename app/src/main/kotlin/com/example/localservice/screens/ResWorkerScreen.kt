package com.example.localservice.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private val ColorPrimary = Color(0xFF006D67)
private val ColorBackground = Color(0xFFF8F6EF)

data class ServiceWorkerModel(

    val workerId: String = "",

    val fullName: String = "",

    val servicesEnglish: List<String> = emptyList(),

    val servicesKannada: List<String> = emptyList(),

    val workerRatings: Double = 0.0,

    val minRate: String = "",

    val workerPhone: String = "",

    val profileImage: String = "",

    val workerStatus: String = "offline",

    val preferredArea: String = ""

)

@Composable
fun ResWorkerScreen(

    serviceNameEnglish: String,

    serviceNameKannada: String,

    language: String = "en",

    onLanguageChange: (String) -> Unit = {},

    darkMode: Boolean = false,

    onDarkModeToggle: () -> Unit = {},

    onBack: () -> Unit = {},

    onWorkerClick: (String) -> Unit = {}

) {

    val firestore = FirebaseFirestore.getInstance()

    val currentUser = Firebase.auth.currentUser

    val workerList = remember {
        mutableStateListOf<ServiceWorkerModel>()
    }

    var residentArea by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {

        val uid = currentUser?.uid ?: return@LaunchedEffect

        firestore
            .collection("residents")
            .document(uid)
            .get()
            .addOnSuccessListener { resident ->

                residentArea =
                    resident.getString("area") ?: ""

                firestore
                    .collection("workers")
                    .get()
                    .addOnSuccessListener { result ->

                        workerList.clear()

                        result.documents.forEach { document ->

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

                            val containsService =

                                englishServices.contains(serviceNameEnglish)

                                        ||

                                        kannadaServices.contains(serviceNameKannada)

                            if (containsService) {

                                firestore
                                    .collection("worker_status")
                                    .document(document.id)
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

                                        workerList.add(

                                            ServiceWorkerModel(

                                                workerId = document.id,

                                                fullName =
                                                    document.getString("fullName")
                                                        ?: "",

                                                servicesEnglish = englishServices,

                                                servicesKannada = kannadaServices,

                                                workerRatings =
                                                    document.getDouble("rating")
                                                        ?: 0.0,

                                                minRate = minimumRate,

                                                workerPhone =
                                                    document.getString("contactNumber")
                                                        ?: "",

                                                profileImage =
                                                    document.getString("profilePic")
                                                        ?: "",

                                                workerStatus =
                                                    if (isAvailable)
                                                        "online"
                                                    else
                                                        "offline",

                                                preferredArea =
                                                    document.getString("preferredAreasText")
                                                        ?: ""
                                            )
                                        )

                                        workerList.sortWith(

                                            compareByDescending<ServiceWorkerModel> {

                                                it.workerStatus == "online"

                                            }.thenByDescending {

                                                it.preferredArea.contains(
                                                    residentArea,
                                                    ignoreCase = true
                                                )
                                            }
                                        )
                                    }
                            }
                        }

                    }
            }
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)

    ) {

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

            IconButton(
                onClick = onBack
            ) {

                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(

                    text = if (language == "en")
                        serviceNameEnglish
                    else
                        serviceNameKannada,

                    color = ColorPrimary,

                    fontWeight = FontWeight.ExtraBold,

                    fontSize = 22.sp
                )

                Text(

                    text = "${workerList.size} ${
                        if (language == "en")
                            "workers nearby"
                        else
                            "ವರ್ಕರ್ ಹತ್ತಿರದಲ್ಲಿದ್ದಾರೆ"
                    }",

                    color = Color.Gray,

                    fontSize = 13.sp
                )
            }

            Surface(

                shape = CircleShape,

                color = Color(0xFFEAF2EE),

                modifier = Modifier.size(46.dp)

            ) {

                IconButton(
                    onClick = onDarkModeToggle
                ) {

                    Icon(

                        if (darkMode)
                            Icons.Outlined.LightMode
                        else
                            Icons.Default.DarkMode,

                        contentDescription = null,

                        tint = ColorPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.size(10.dp))

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
                        horizontal = 18.dp,
                        vertical = 10.dp
                    ),

                    color = ColorPrimary,

                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(

            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(
                horizontal = 18.dp,
                vertical = 10.dp
            ),

            verticalArrangement = Arrangement.spacedBy(18.dp)

        ) {

            items(workerList) { worker ->

                ServiceWorkerCard(

                    worker = worker,

                    language = language,

                    onClick = {

                        onWorkerClick(worker.workerId)
                    }
                )
            }
        }
    }
}

@Composable
fun ServiceWorkerCard(

    worker: ServiceWorkerModel,

    language: String,

    onClick: () -> Unit

) {

    val context = LocalContext.current

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable(
                enabled =
                    worker.workerStatus.lowercase() == "online"
            ) {

                onClick()
            },
        shape = RoundedCornerShape(28.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Row(

            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Box {

                Image(

                    painter = rememberAsyncImagePainter(worker.profileImage),

                    contentDescription = null,

                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape),

                    contentScale = ContentScale.Crop
                )

                Surface(

                    color = if (
                        worker.workerStatus.lowercase() == "online"
                    )
                        Color(0xFF15B84A)
                    else
                        Color.DarkGray,

                    shape = RoundedCornerShape(18.dp),

                    modifier = Modifier
                        .align(Alignment.BottomCenter)

                ) {

                    Text(

                        text = if (
                            worker.workerStatus.lowercase() == "online"
                        )
                            if (language == "en")
                                "ONLINE"
                            else
                                "ಆನ್‌ಲೈನ್"
                        else
                            if (language == "en")
                                "OFFLINE"
                            else
                                "ಆಫ್",

                        color = Color.White,

                        fontSize = 9.sp,

                        fontWeight = FontWeight.Bold,

                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 4.dp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.padding(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 6.dp)
            ) {

                Text(

                    text = worker.fullName,

                    fontSize = 18.sp,

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF4B4B4B)
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(

                    text = if (language == "en")
                        worker.servicesEnglish.joinToString(", ")
                    else
                        worker.servicesKannada.joinToString(", "),

                    color = Color.Gray,

                    fontSize = 11.sp,

                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(

                        text = worker.workerRatings.toString(),

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF4B4B4B)
                    )

                    Spacer(modifier = Modifier.padding(3.dp))

                    repeat(5) {

                        Icon(

                            Icons.Default.ThumbUp,

                            contentDescription = null,

                            tint = Color(0xFF13B54B),

                            modifier = Modifier.size(14.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Box(

                        modifier = Modifier
                            .height(24.dp)
                            .width(1.dp)
                            .background(Color.LightGray)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(

                        text = if (language == "en")
                            "Min Rate:\n₹${worker.minRate}"
                        else
                            "ಕನಿಷ್ಠ ದರ:\n₹${worker.minRate}",

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF4B4B4B),

                        fontSize = 11.sp
                    )
                }
            }

            Surface(

                shape = RoundedCornerShape(20.dp),

                color = Color(0xFFF0F0F0),

                modifier = Modifier
                    .size(58.dp)
                    .clickable {

                        val intent = Intent(

                            Intent.ACTION_DIAL,

                            Uri.parse("tel:${worker.workerPhone}")
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

                        tint = Color(0xFF555555),

                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}