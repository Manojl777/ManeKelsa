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
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.layout.fillMaxHeight
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private val ColorPrimary = Color(0xFF006D67)
private val ColorBackground = Color(0xFFF8F6EF)

data class NearbyWorkerModel(

    val workerId: String = "",

    val fullName: String = "",

    val services: List<String> = emptyList(),

    val workerRatings: Double = 0.0,

    val minRate: String = "",

    val workerPhone: String = "",

    val profileImage: String = "",

    val workerStatus: String = "offline",

    val preferredArea: String = ""

)

@Composable
fun AllWorkers(

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
        mutableStateListOf<NearbyWorkerModel>()
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

                                    val translatedServices = mutableListOf<String>()

                                    serviceCodes.forEach { code ->

                                        when(code.trim()) {

                                            "svcHouseCleaning" ->

                                                translatedServices.add(
                                                    if (language == "en")
                                                        "House Cleaning"
                                                    else
                                                        "ಮನೆ ಸ್ವಚ್ಛಗೊಳಿಸುವಿಕೆ"
                                                )

                                            "svcDeepCleaning" ->

                                                translatedServices.add(
                                                    if (language == "en")
                                                        "Deep Cleaning"
                                                    else
                                                        "ಡೀಪ್ ಕ್ಲೀನಿಂಗ್"
                                                )

                                            "svcElectricalRepair" ->

                                                translatedServices.add(
                                                    if (language == "en")
                                                        "Electrical Repair"
                                                    else
                                                        "ಎಲೆಕ್ಟ್ರಿಕಲ್ ರಿಪೇರಿ"
                                                )

                                            "svcElectricInstallation" ->

                                                translatedServices.add(
                                                    if (language == "en")
                                                        "Electric Installation"
                                                    else
                                                        "ಎಲೆಕ್ಟ್ರಿಕ್ ಇನ್‌ಸ್ಟಾಲೇಶನ್"
                                                )

                                            "svcPlumbing" ->

                                                translatedServices.add(
                                                    if (language == "en")
                                                        "Plumbing"
                                                    else
                                                        "ಪ್ಲಂಬಿಂಗ್"
                                                )

                                            "svcApplianceRepair" ->

                                                translatedServices.add(
                                                    if (language == "en")
                                                        "Appliance Repair"
                                                    else
                                                        "ಉಪಕರಣ ದುರಸ್ತಿ"
                                                )

                                            "svcACService" ->

                                                translatedServices.add(
                                                    if (language == "en")
                                                        "AC Service"
                                                    else
                                                        "ಎಸಿ ಸೇವೆ"
                                                )

                                            "svcPainting" ->

                                                translatedServices.add(
                                                    if (language == "en")
                                                        "Painting"
                                                    else
                                                        "ಪೇಂಟಿಂಗ್"
                                                )

                                            "svcCarpentry" ->

                                                translatedServices.add(
                                                    if (language == "en")
                                                        "Carpentry"
                                                    else
                                                        "ಕಾರ್ಪೆಂಟ್ರಿ"
                                                )

                                            "svcGardening" ->

                                                translatedServices.add(
                                                    if (language == "en")
                                                        "Gardening"
                                                    else
                                                        "ತೋಟಗಾರಿಕೆ"
                                                )

                                            "svcVehicleWash" ->

                                                translatedServices.add(
                                                    if (language == "en")
                                                        "Vehicle Wash"
                                                    else
                                                        "ವಾಹನ ತೊಳೆಯುವುದು"
                                                )
                                        }
                                    }

                                    workerList.add(

                                        NearbyWorkerModel(

                                            workerId = workerId,

                                            fullName =
                                                document.getString("fullName")
                                                    ?: "",

                                            services = translatedServices,

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

                                        compareByDescending<NearbyWorkerModel> {

                                            it.preferredArea.contains(
                                                residentArea,
                                                ignoreCase = true
                                            )

                                        }.thenByDescending {

                                            it.workerStatus == "online"
                                        }
                                    )
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
                        "Nearby Workers"
                    else
                        "ಹತ್ತಿರದ ಕೆಲಸಗಾರರು",

                    color = ColorPrimary,

                    fontWeight = FontWeight.ExtraBold,

                    fontSize = 22.sp
                )

                Text(

                    text = "${workerList.size} ${
                        if (language == "en")
                            "PROFESSIONALS FOUND"
                        else
                            "ವೃತ್ತಿಪರರು ಕಂಡುಬಂದಿದ್ದಾರೆ"
                    }",

                    color = Color.LightGray,

                    fontWeight = FontWeight.Bold,

                    letterSpacing = 1.sp,

                    fontSize = 11.sp
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
                        horizontal = 18.dp,
                        vertical = 10.dp
                    ),

                    color = ColorPrimary,

                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        LazyColumn(

            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(
                horizontal = 18.dp,
                vertical = 8.dp
            ),

            verticalArrangement = Arrangement.spacedBy(18.dp)

        ) {

            items(workerList) { worker ->

                NearbyWorkerCard(

                    worker = worker,

                    language = language,

                    onClick = {

                        if (
                            worker.workerStatus.lowercase() == "online"
                        ) {

                            onWorkerClick(worker.workerId)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun NearbyWorkerCard(

    worker: NearbyWorkerModel,

    language: String,

    onClick: () -> Unit

) {

    val context = LocalContext.current

    val isOnline =
        worker.workerStatus.lowercase() == "online"

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .clickable(
                enabled = isOnline
            ) {
                onClick()
            },

        shape = RoundedCornerShape(28.dp),

        colors = CardDefaults.cardColors(

            containerColor =
                if (isOnline)
                    Color.White
                else
                    Color(0xFFF1F1F1)
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Row(

            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .alpha(
                    if (isOnline) 1f else 0.5f
                ),

            verticalAlignment = Alignment.CenterVertically

        ) {

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

                    Image(

                        imageVector = Icons.Default.Person,

                        contentDescription = null,

                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                    )
                }

                Surface(

                    color = if (isOnline)
                        Color(0xFF10B84B)
                    else
                        Color(0xFF4A4A4A),

                    shape = RoundedCornerShape(18.dp),

                    modifier = Modifier
                        .align(Alignment.BottomCenter)

                ) {

                    Text(

                        text = if (isOnline)
                            if (language == "en")
                                "ONLINE"
                            else
                                "ಆನ್‌ಲೈನ್"
                        else
                            if (language == "en")
                                "OFFLINE"
                            else
                                "ಆಫ್‌ಲೈನ್",

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

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(

                    text = worker.fullName,

                    fontSize = 20.sp,

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF4B4B4B)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(

                    text = worker.services.joinToString(", "),

                    color = Color.Gray,

                    fontSize = 13.sp,

                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(

                        text = worker.workerRatings.toString(),

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF4B4B4B)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    repeat(5) { index ->

                        Icon(

                            Icons.Default.ThumbUp,

                            contentDescription = null,

                            tint = if (
                                index < worker.workerRatings.toInt()
                            )
                                Color(0xFF10B84B)
                            else
                                Color.LightGray,

                            modifier = Modifier.size(14.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Box(

                        modifier = Modifier
                            .fillMaxHeight(0.3f)
                            .width(1.dp)
                            .background(Color.LightGray)
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Text(

                        text = if (language == "en")
                            "Min Rate:\n₹${worker.minRate}"
                        else
                            "ಕನಿಷ್ಠ ದರ:\n₹${worker.minRate}",

                        fontWeight = FontWeight.Bold,

                        color = ColorPrimary,

                        fontSize = 13.sp
                    )
                }
            }

            Surface(

                shape = RoundedCornerShape(20.dp),

                color =
                    if (isOnline)
                        Color(0xFFEAF2EE)
                    else
                        Color(0xFFE0E0E0),

                modifier = Modifier
                    .size(58.dp)
                    .clickable(
                        enabled = isOnline
                    ) {

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

                        tint = ColorPrimary,

                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}