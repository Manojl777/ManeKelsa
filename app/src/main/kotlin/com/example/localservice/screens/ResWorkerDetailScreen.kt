package com.example.localservice.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
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
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.asImageBitmap
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

private val ColorPrimary = Color(0xFF006D67)
private val ColorBackground = Color(0xFFF8F6EF)

@Composable
fun ResWorkerDetailScreen(

    workerId: String,

    language: String = "en",

    darkMode: Boolean = false,

    onDarkModeToggle: () -> Unit = {},

    onLanguageChange: (String) -> Unit = {},

    onBack: () -> Unit = {}

) {

    val firestore = FirebaseFirestore.getInstance()

    val context = LocalContext.current

    var profileImage by remember {
        mutableStateOf("")
    }

    var workerName by remember {
        mutableStateOf("")
    }

    var workerStatus by remember {
        mutableStateOf("offline")
    }

    var workerRating by remember {
        mutableStateOf(0.0)
    }

    var gender by remember {
        mutableStateOf("")
    }

    var age by remember {
        mutableStateOf("")
    }

    var address by remember {
        mutableStateOf("")
    }

    var contact by remember {
        mutableStateOf("")
    }

    var services by remember {
        mutableStateOf(listOf<String>())
    }

    var workerCode by remember {
        mutableStateOf("")
    }

    var selectedRating by remember {
        mutableStateOf(0)
    }

    var callEnabled by remember {
        mutableStateOf(false)
    }

    var showSubmitPopup by remember {
        mutableStateOf(false)
    }

    var showThankYouPopup by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {

        firestore
            .collection("workers")
            .document(workerId)
            .get()
            .addOnSuccessListener { workerDoc ->

                firestore
                    .collection("worker_status")
                    .document(workerId)
                    .get()
                    .addOnSuccessListener { statusDoc ->

                        profileImage =
                            workerDoc.getString("profilePic")
                                ?: ""

                        workerName =
                            workerDoc.getString("fullName")
                                ?: ""

                        workerRating =
                            workerDoc.getDouble("rating")
                                ?: 0.0

                        gender =
                            workerDoc.getString("gender")
                                ?: ""

                        age =
                            workerDoc.getString("dob")
                                ?: ""
                        address =
                            workerDoc.getString("area")
                                ?: ""

                        contact =
                            workerDoc.getString("contactNumber")
                                ?: ""

                        val serviceCodes =
                            workerDoc.get("serviceTypes")
                                    as? List<String>
                                ?: emptyList()

                        services = serviceCodes.map { code ->

                            when(code) {

                                "svcHouseCleaning" ->
                                    if (language == "en")
                                        "House Cleaning"
                                    else
                                        "ಮನೆ ಸ್ವಚ್ಛಗೊಳಿಸುವಿಕೆ"

                                "svcDeepCleaning" ->
                                    if (language == "en")
                                        "Deep Cleaning"
                                    else
                                        "ಡೀಪ್ ಕ್ಲೀನಿಂಗ್"

                                "svcElectricalRepair" ->
                                    if (language == "en")
                                        "Electrical Repair"
                                    else
                                        "ಎಲೆಕ್ಟ್ರಿಕಲ್ ರಿಪೇರಿ"

                                "svcElectricInstallation" ->
                                    if (language == "en")
                                        "Electric Installation"
                                    else
                                        "ಎಸಿ ಸೇವೆ"

                                "svcPlumbing" ->
                                    if (language == "en")
                                        "Plumbing"
                                    else
                                        "ಪ್ಲಂಬಿಂಗ್"

                                "svcApplianceRepair" ->
                                    if (language == "en")
                                        "Appliance Repair"
                                    else
                                        "ಉಪಕರಣ ದುರಸ್ತಿ"

                                "svcACService" ->
                                    if (language == "en")
                                        "AC Service"
                                    else
                                        "ಎಸಿ ಸೇವೆ"

                                "svcPainting" ->
                                    if (language == "en")
                                        "Painting"
                                    else
                                        "ಪೇಂಟಿಂಗ್"

                                "svcCarpentry" ->
                                    if (language == "en")
                                        "Carpentry"
                                    else
                                        "ಕಾರ್ಪೆಂಟ್ರಿ"

                                "svcGardening" ->
                                    if (language == "en")
                                        "Gardening"
                                    else
                                        "ತೋಟಗಾರಿಕೆ"

                                "svcVehicleWash" ->
                                    if (language == "en")
                                        "Vehicle Wash"
                                    else
                                        "ವಾಹನ ತೊಳೆಯುವುದು"

                                else -> code
                            }
                        }

                        workerCode =
                            workerDoc.getString("id")
                                ?: ""

                        workerStatus =
                            if (
                                statusDoc.getBoolean("isAvailable") == true
                            )
                                "online"
                            else
                                "offline"
                    }
            }
    }

    if (showSubmitPopup) {

        AlertDialog(

            onDismissRequest = {},

            confirmButton = {

                TextButton(

                    onClick = {

                        val ratingMap = hashMapOf(

                            "rating" to selectedRating,

                            "timestamp" to FieldValue.serverTimestamp()
                        )

                        firestore
                            .collection("workers")
                            .document(workerId)
                            .collection("worker_ratings")
                            .add(ratingMap)

                        firestore
                            .collection("workers")
                            .document(workerId)
                            .get()
                            .addOnSuccessListener { document ->

                                val currentRating =
                                    document.getDouble("worker_ratings") ?: 0.0

                                val newAverage =
                                    (currentRating + selectedRating) / 2

                                firestore
                                    .collection("workers")
                                    .document(workerId)
                                    .update(
                                        "worker_ratings",
                                        newAverage
                                    )
                            }

                        showSubmitPopup = false

                        showThankYouPopup = true
                    }
                ) {

                    Text(

                        if (language == "en")
                            "Submit"
                        else
                            "ಸಲ್ಲಿಸಿ"
                    )
                }
            },

            title = {

                Text(

                    if (language == "en")
                        "Submit Rating"
                    else
                        "ರೇಟಿಂಗ್ ಸಲ್ಲಿಸಿ"
                )
            },

            text = {

                Text(

                    if (language == "en")
                        "Are you sure you want to rate this worker?"
                    else
                        "ಈ ವರ್ಕರ್‌ಗೆ ರೇಟಿಂಗ್ ನೀಡಲು ಖಚಿತವೇ?"
                )
            }
        )
    }

    if (showThankYouPopup) {

        AlertDialog(

            onDismissRequest = {},

            confirmButton = {

                TextButton(

                    onClick = {

                        showThankYouPopup = false
                    }
                ) {

                    Text(

                        if (language == "en")
                            "OK"
                        else
                            "ಸರಿ"
                    )
                }
            },

            title = {

                Text(

                    if (language == "en")
                        "Thank You"
                    else
                        "ಧನ್ಯವಾದಗಳು"
                )
            },

            text = {

                Text(

                    if (language == "en")
                        "Thank you for rating this worker."
                    else
                        "ರೇಟಿಂಗ್ ನೀಡಿದಕ್ಕಾಗಿ ಧನ್ಯವಾದಗಳು"
                )
            }
        )
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
            .verticalScroll(rememberScrollState())

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

            Text(

                text = if (language == "en")
                    "Worker Details"
                else
                    "ವರ್ಕರ್ ವಿವರಗಳು",

                color = ColorPrimary,

                fontWeight = FontWeight.ExtraBold,

                fontSize = 22.sp,

                modifier = Modifier.weight(1f)
            )

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
            ){

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

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box {

                val imageBytes = try {

                    Base64.decode(
                        profileImage,
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
                            .size(150.dp)
                            .clip(RoundedCornerShape(34.dp)),

                        contentScale = ContentScale.Crop
                    )

                } else {

                    Image(

                        imageVector = Icons.Default.Person,

                        contentDescription = null,

                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(34.dp))
                    )
                }

                Surface(

                    shape = CircleShape,

                    color = Color.White,

                    shadowElevation = 8.dp,

                    modifier = Modifier
                        .align(Alignment.BottomEnd)

                ) {

                    Icon(

                        Icons.Default.ThumbUp,

                        contentDescription = null,

                        tint = Color(0xFF10B84B),

                        modifier = Modifier
                            .padding(12.dp)
                            .size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(

                text = workerName,

                fontSize = 26.sp,

                fontWeight = FontWeight.ExtraBold,

                color = ColorPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Surface(

                shape = RoundedCornerShape(30.dp),

                color = Color(0xFFE8FAEC)

            ) {

                Text(

                    text = if (
                        workerStatus.lowercase() == "online"
                    )
                        if (language == "en")
                            "  ● LIVE: AVAILABLE  "
                        else
                            "  ● ಲಭ್ಯವಿದೆ  "
                    else
                        if (language == "en")
                            "  ● OFFLINE  "
                        else
                            "  ● ಆಫ್‌ಲೈನ್  ",

                    color = if (
                        workerStatus.lowercase() == "online"
                    )
                        Color(0xFF10B84B)
                    else
                        Color.DarkGray,

                    fontWeight = FontWeight.Bold,

                    modifier = Modifier.padding(
                        horizontal = 14.dp,
                        vertical = 8.dp
                    )
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(

                    text = workerRating.toString(),

                    fontWeight = FontWeight.Bold,

                    fontSize = 28.sp,

                    color = Color(0xFF4B4B4B)
                )

                Spacer(modifier = Modifier.width(10.dp))

                repeat(5) {

                    Icon(

                        Icons.Default.ThumbUp,

                        contentDescription = null,

                        tint = if (
                            it < workerRating.toInt()
                        )
                            Color(0xFF10B84B)
                        else
                            Color.LightGray,

                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),

            shape = RoundedCornerShape(30.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )

        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                WorkerInfoRow(
                    icon = Icons.Default.Person,
                    title1 =
                        if (language == "en")
                            "WORKER ID"
                        else
                            "ವರ್ಕರ್ ಐಡಿ",
                    value1 = workerCode,
                    title2 =
                        if (language == "en")
                            "GENDER"
                        else
                            "ಲಿಂಗ",
                    value2 = gender
                )

                DividerLine()

                WorkerInfoRow(
                    icon = Icons.Default.CalendarMonth,
                    title1 =
                        if (language == "en")
                            "DOB"
                        else
                            "ಜನ್ಮ ದಿನಾಂಕ",
                    value1 = age
                )

                DividerLine()

                WorkerInfoRow(
                    icon = Icons.Default.LocationOn,
                    title1 =
                        if (language == "en")
                            "ADDRESS"
                        else
                            "ವಿಳಾಸ",
                    value1 = address
                )

                DividerLine()

                WorkerInfoRow(
                    icon = Icons.Default.Call,
                    title1 =
                        if (language == "en")
                            "CONTACT"
                        else
                            "ಸಂಪರ್ಕ",
                    value1 = "+91 $contact"
                )

                DividerLine()

                Row {

                    InfoIconBox(
                        icon = Icons.Default.ThumbUp,
                        color = Color(0xFFF9F0B9)
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {

                        Text(

                            text =
                                if (language == "en")
                                    "SKILLS & SERVICES"
                                else
                                    "ಸೇವೆಗಳು",

                            color = Color.LightGray,

                            fontWeight = FontWeight.Bold,

                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Column {

                            services.chunked(2).forEach { rowServices ->

                                Row(

                                    modifier = Modifier.padding(bottom = 8.dp)
                                ) {

                                    rowServices.forEach { service ->

                                        Surface(

                                            shape = RoundedCornerShape(18.dp),

                                            color = Color(0xFFF5F5F5),

                                            modifier = Modifier.padding(end = 8.dp)

                                        ) {

                                            Text(

                                                text = service.uppercase(),

                                                modifier = Modifier.padding(
                                                    horizontal = 10.dp,
                                                    vertical = 6.dp
                                                ),

                                                fontSize = 9.sp,

                                                fontWeight = FontWeight.Bold,

                                                color = Color.Gray,

                                                maxLines = 3
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),

            shape = RoundedCornerShape(30.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )

        ) {

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),

                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Text(

                    text =
                        if (language == "en")
                            "Rate this Professional"
                        else
                            "ಈ ವರ್ಕರ್‌ಗೆ ರೇಟಿಂಗ್ ನೀಡಿ",

                    fontWeight = FontWeight.Bold,

                    fontSize = 24.sp,

                    color = Color(0xFF4B4B4B)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(

                    text =
                        if (language == "en")
                            "Call the professional first to enable rating"
                        else
                            "ರೇಟಿಂಗ್ ಮಾಡಲು ಮೊದಲು ಕರೆ ಮಾಡಿ",

                    color = Color.Gray,

                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row {

                    repeat(5) { index ->

                        Surface(

                            shape = RoundedCornerShape(18.dp),

                            color = Color(0xFFF8F8F8),

                            modifier = Modifier
                                .padding(horizontal = 6.dp)
                                .clickable {

                                    if (callEnabled) {

                                        selectedRating = index + 1

                                        showSubmitPopup = true
                                    }
                                }

                        ) {

                            Icon(

                                Icons.Default.ThumbUp,

                                contentDescription = null,

                                tint = if (
                                    index < selectedRating
                                )
                                    Color(0xFF10B84B)
                                else
                                    Color.LightGray,

                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(32.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(

            onClick = {

                callEnabled = true

                val intent = Intent(

                    Intent.ACTION_DIAL,

                    Uri.parse("tel:$contact")
                )

                context.startActivity(intent)
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .height(72.dp),

            shape = RoundedCornerShape(28.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = ColorPrimary
            )

        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    Icons.Default.Call,
                    contentDescription = null,
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(

                    text = if (language == "en")
                        "Call Worker Now"
                    else
                        "ಈಗ ಕರೆ ಮಾಡಿ",

                    fontSize = 24.sp,

                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))
    }
}

@Composable
fun WorkerInfoRow(

    icon: androidx.compose.ui.graphics.vector.ImageVector,

    title1: String,

    value1: String,

    title2: String = "",

    value2: String = ""

) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        InfoIconBox(
            icon = icon
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ){

            Text(

                text = title1,

                color = Color.LightGray,

                fontWeight = FontWeight.Bold,

                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(

                text = value1,

                color = ColorPrimary,

                fontWeight = FontWeight.Bold,

                fontSize = 18.sp
            )
        }

        if (title2.isNotEmpty()) {

            Column(

                modifier = Modifier
                    .weight(1f)
                    .padding(start = 18.dp)
            ) {

                Text(

                    text = title2,

                    color = Color.LightGray,

                    fontWeight = FontWeight.Bold,

                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(

                    text = value2,

                    color = ColorPrimary,

                    fontWeight = FontWeight.Bold,

                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun DividerLine() {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 18.dp)
            .height(1.dp)
            .background(Color(0xFFF0F0F0))
    )
}

@Composable
fun InfoIconBox(

    icon: androidx.compose.ui.graphics.vector.ImageVector,

    color: Color = Color(0xFFEAF2EE)

) {

    Surface(

        shape = RoundedCornerShape(18.dp),

        color = color,

        modifier = Modifier.size(54.dp)

    ) {

        Box(
            contentAlignment = Alignment.Center
        ) {

            Icon(

                icon,

                contentDescription = null,

                tint = ColorPrimary,

                modifier = Modifier.size(28.dp)
            )
        }
    }
}