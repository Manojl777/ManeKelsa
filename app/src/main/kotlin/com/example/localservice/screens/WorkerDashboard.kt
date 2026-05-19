package com.example.localservice.screens

import android.util.Base64
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.localservice.i18n.t
import java.util.Calendar
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
private val BgColor = Color(0xFFF7F4ED)
private val Orange = Color(0xFFFF5A1F)
private val Teal = Color(0xFF006D6F)


@Composable
fun WorkerDashboard(

    language: String = "en",

    onLanguageChange: (String) -> Unit = {},

    darkTheme: Boolean = false,

    onThemeChange: (Boolean) -> Unit = {},

    workerName: String = "",

    workerArea: String = "",

    workerServices: List<String> = emptyList(),

    workerProfile: String = "",

    totalEarnings: Int = 0,

    workerRating: Double = 4.5,

    onSaveSessionEarning: (Int) -> Unit = {},

    onOpenEarnings: () -> Unit = {},

    onOpenAccount: () -> Unit = {},

    onOpenSettings: () -> Unit = {},

    onLogout: () -> Unit = {},

    workerId: String = "",

    initialOnlineStatus: Boolean = false,

    initialRate: Int = 0,

    onUpdateStatus: (Boolean, Int) -> Unit = { _, _ -> },


) {

    val tr: (String) -> String = {
        t(language, it)
    }
    var isOnline by remember(initialOnlineStatus) {
        mutableStateOf(initialOnlineStatus)
    }

    var currentRate by remember(initialRate) {
        mutableStateOf(initialRate)
    }

    var showRateDialog by remember {
        mutableStateOf(false)
    }

    var showEarningDialog by remember {
        mutableStateOf(false)
    }

    var accountExpanded by remember {
        mutableStateOf(false)
    }

    val hour =
        Calendar.getInstance()
            .get(Calendar.HOUR_OF_DAY)

    val greeting = when {

        hour < 12 -> {

            if (language == "kn")
                "ಶುಭೋದಯ"
            else
                "Good Morning"
        }

        hour < 17 -> {

            if (language == "kn")
                "ಶುಭ ಮಧ್ಯಾಹ್ನ"
            else
                "Good Afternoon"
        }

        else -> {

            if (language == "kn")
                "ಶುಭ ಸಂಜೆ"
            else
                "Good Evening"
        }
    }

    if (showRateDialog) {

        SetRateDialog(
            language = language,
            onDismiss = {
                showRateDialog = false
            },
            onConfirm = {

                currentRate = it
                isOnline = true
                onUpdateStatus(true, it)

                showRateDialog = false
            }
        )
    }

    if (showEarningDialog) {

        SessionEarningDialog(
            language = language,
            onDismiss = {
                showEarningDialog = false
            },
            onConfirm = {

                onSaveSessionEarning(it)

                currentRate = 0
                isOnline = false
                onUpdateStatus(false, 0)
                showEarningDialog = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .verticalScroll(rememberScrollState())
    ) {

        TopBar(
            onOpenAccount = {
                accountExpanded = true
            },

            onOpenIDCard = {

                accountExpanded = false
                onOpenAccount()
            },

            onOpenEarnings = {

                accountExpanded = false
                onOpenEarnings()
            },

            onOpenSettings = {

                accountExpanded = false
                onOpenSettings()
            },

            onLogout = {

                accountExpanded = false
                onLogout()
            },
            language = language,
            workerProfile = workerProfile,
            darkTheme = darkTheme,

            accountExpanded = accountExpanded,

            onDismissAccount = {
                accountExpanded = false
            },

            onThemeChange = {
                onThemeChange(!darkTheme)
            },

            onLanguageChange = {
                onLanguageChange(
                    if (language == "en") "kn" else "en"
                )
            },
        )

        Column(
            modifier = Modifier.padding(
                horizontal = 22.dp,
                vertical = 20.dp
            )
        ) {

            Column {

                Text(
                    text = "$greeting, $workerName!",
                    color = Color(0xFF4B4B45),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp,
                    lineHeight = 36.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text =
                            when(workerServices.firstOrNull()) {

                                "svcElectricalInstallation" -> "ELECTRICAL INSTALLATION"

                                "svcElectricalRepair" -> "ELECTRICAL REPAIR"

                                "svcVehicleWash" -> "VEHICLE WASH"

                                "svcGardening" -> "GARDENING"

                                "svcPlumbing" -> "PLUMBING"

                                "svcPainting" -> "PAINTING"

                                "svcCarpentry" -> "CARPENTRY"

                                else ->
                                    workerServices.firstOrNull()
                                        ?: ""
                            },

                        color = Orange,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    if (workerArea.isNotEmpty()) {

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "•",
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = workerArea,
                            color = Color(0xFF9B9B9B),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            StatusCard(
                language = language,
                isOnline = isOnline,
                currentRate = currentRate,
                onToggle = {

                    if (isOnline) {

                        showEarningDialog = true

                    } else {

                        showRateDialog = true
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                StatCard(
                    modifier = Modifier.weight(1f),
                    title = tr("totalEarnings"),
                    value =
                        if (language == "kn")
                            "₹${convertToKannadaNumbers(totalEarnings.toString())}"
                        else
                            "₹$totalEarnings",
                    valueColor = Orange,
                    onClick = onOpenEarnings
                )

                StatCard(
                    modifier = Modifier.weight(1f),
                    title = tr("rating"),
                    value = String.format("%.1f", workerRating),
                    valueColor = Teal,
                    icon = true
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onOpenEarnings()
                    },
                shape = RoundedCornerShape(28.dp),
                color = Color(0xFFFFF6F1),
                border = BorderStroke(
                    1.dp,
                    Color(0xFFFFE0D1)
                )
            ) {

                Row(
                    modifier = Modifier.padding(
                        horizontal = 24.dp,
                        vertical = 22.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFEFE7)),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            Icons.Default.History,
                            contentDescription = null,
                            tint = Orange
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Text(
                        text = tr("earningHistory"),
                        color = Orange,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    language: String,
    workerProfile: String,
    darkTheme: Boolean,
    onThemeChange: () -> Unit,
    onLanguageChange: () -> Unit,
    accountExpanded: Boolean,
    onDismissAccount: () -> Unit,
    onOpenAccount: () -> Unit,
    onOpenIDCard: () -> Unit,
    onOpenEarnings: () -> Unit,
    onOpenSettings: () -> Unit,
    onLogout: () -> Unit

) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Orange),

                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "▣",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text =
                    if (language == "kn")
                        "ಮನೆಕೆಲಸ W"
                    else
                        "Mane Kelsa W",

                color = Orange,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Surface(
            modifier = Modifier.size(36.dp),
            shape = CircleShape,
            color = Color(0xFFF4F7F6),
            border = BorderStroke(
                1.dp,
                Color(0xFFDCE7E5)
            )
        ) {

            IconButton(
                onClick = onThemeChange,
                modifier = Modifier.size(36.dp)
            ) {

                Icon(
                    imageVector =
                        if (darkTheme)
                            Icons.Default.LightMode
                        else
                            Icons.Default.DarkMode,

                    contentDescription = null,
                    tint = Teal,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color(0xFFF4F7F6),
            border = BorderStroke(
                1.dp,
                Color(0xFFDCE7E5)
            )
        ) {

            Text(
                text =
                    if (language == "en")
                        "ಕನ್ನಡ"
                    else
                        "ENGLISH",

                modifier = Modifier
                    .clickable {
                        onLanguageChange()
                    }
                    .padding(
                        horizontal = 14.dp,
                        vertical = 8.dp
                    ),

                color = Teal,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box {

            if (workerProfile.isNotEmpty()) {

                AsyncImage(

                    model = Base64.decode(
                        workerProfile,
                        Base64.DEFAULT
                    ),

                    contentDescription = null,

                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .clickable {
                            onOpenAccount()
                        },

                    contentScale = ContentScale.Crop
                )

            } else {

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable {
                            onOpenAccount()
                        },

                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "M",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            DropdownMenu(
                expanded = accountExpanded,

                onDismissRequest = {
                    onDismissAccount()
                },

                offset = DpOffset(
                    x = (0).dp,
                    y = 0.dp
                )
            ) {

                WorkerAccountDropdown(

                    language = language,

                    expanded = true,

                    onDismiss = {
                        onDismissAccount()
                    },

                    onOpenIDCard = {
                        onOpenIDCard()
                    },

                    onOpenEarnings = {
                        onOpenEarnings()
                    },

                    onOpenSettings = {
                        onOpenSettings()
                    },

                    onLogout = {
                        onLogout()
                    }
                )
            }
        }
    }
}
@Composable
private fun StatusCard(
    language: String,
    isOnline: Boolean,
    currentRate: Int,
    onToggle: () -> Unit
) {

    val infiniteTransition =
        rememberInfiniteTransition()

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        )
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        color = Color.White,
        shadowElevation = 4.dp
    ) {

        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(18.dp)
                    .alpha(alpha)
                    .background(
                        if (isOnline)
                            Color.Green
                        else
                            Color.Red,
                        CircleShape
                    )
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text =
                        if (isOnline)
                            if (language == "kn") "ಆನ್ಲೈನ್"
                            else "ONLINE"
                        else
                            if (language == "kn") "ಆಫ್ಲೈನ್"
                            else "OFFLINE",

                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = Color(0xFF4B4B45)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text =
                        if (isOnline)
                            if (language == "kn")
                                "ಪ್ರಸ್ತುತ ಲಭ್ಯ"
                            else
                                "Currently Available"
                        else
                            if (language == "kn")
                                "ಪ್ರಸ್ತುತ ಲಭ್ಯವಿಲ್ಲ"
                            else
                                "Currently Unavailable",

                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text =
                        if (language == "kn")
                            "ಕನಿಷ್ಠ ದರ"
                        else
                            "Minimum Rate",

                    color = Color.LightGray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text =
                        if (currentRate == 0)
                            "NIL"
                        else
                            "₹$currentRate",

                    color = Teal,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Switch(
                checked = isOnline,
                onCheckedChange = {
                    onToggle()
                },
                modifier = Modifier.scale(0.8f)
            )
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    valueColor: Color,
    icon: Boolean = false,
    onClick: (() -> Unit)? = null
) {

    Surface(
        modifier = modifier
            .height(140.dp)
            .clickable {
                onClick?.invoke()
            },

        shape = RoundedCornerShape(26.dp),

        color = Color.White,

        shadowElevation = 4.dp
    ) {

        Column(
            modifier = Modifier.padding(20.dp),

            verticalArrangement = Arrangement.Center,

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = value,
                    color = valueColor,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp
                )

                if (icon) {

                    Spacer(modifier = Modifier.width(6.dp))

                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEAF8F4)),

                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            Icons.Default.ThumbUp,
                            contentDescription = null,
                            tint = Teal,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                color = Color.LightGray,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
private fun SetRateDialog(
    language: String,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {

    var amount by remember {
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = onDismiss
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),

            shape = RoundedCornerShape(34.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 18.dp
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 28.dp,
                        vertical = 34.dp
                    ),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .background(
                            Color(0xFFEAF4F4),
                            CircleShape
                        ),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        Icons.Default.ThumbUp,
                        contentDescription = null,
                        tint = Teal,
                        modifier = Modifier.size(42.dp)
                    )
                }

                Spacer(modifier = Modifier.height(26.dp))

                Text(
                    text =
                        if(language == "kn")
                            "ಈ ಸೆಷನ್‌ಗಾಗಿ ಕನಿಷ್ಠ ದರವನ್ನು ನಿಗದಿಪಡಿಸಿ"
                        else
                            "Set your Minimum Rate for this session",

                    textAlign = TextAlign.Center,

                    color = Teal,

                    fontWeight = FontWeight.Bold,

                    fontSize = 28.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text =
                        if(language == "kn")
                            "ಈ ದರ ನಿವಾಸಿಗಳಿಗೆ ಗೋಚರಿಸುತ್ತದೆ"
                        else
                            "This rate will be visible to residents for this session.",

                    textAlign = TextAlign.Center,

                    color = Color.LightGray,

                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                OutlinedTextField(

                    value = amount,

                    onValueChange = {
                        amount = it
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(86.dp),

                    textStyle = TextStyle(
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = Teal,
                        textAlign = TextAlign.Center
                    ),

                    leadingIcon = {

                        Text(
                            text = "₹",
                            fontSize = 38.sp,
                            color = Color(0xFFAAC0C0),
                            fontWeight = FontWeight.Bold
                        )
                    },

                    singleLine = true,

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),

                    shape = RoundedCornerShape(22.dp),

                    colors = OutlinedTextFieldDefaults.colors(

                        focusedBorderColor = Color(0xFFE8E8E0),

                        unfocusedBorderColor = Color(0xFFE8E8E0),

                        focusedContainerColor = Color(0xFFF8F8F2),

                        unfocusedContainerColor = Color(0xFFF8F8F2)
                    )
                )

                Spacer(modifier = Modifier.height(34.dp))

                Button(

                    onClick = {

                        if(amount.isNotEmpty()) {

                            onConfirm(amount.toInt())
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),

                    shape = RoundedCornerShape(24.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Teal
                    ),

                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 12.dp
                    )
                ) {

                    Text(
                        text =
                            if(language == "kn")
                                "ಖಚಿತಪಡಿಸಿ"
                            else
                                "Confirm",

                        fontSize = 24.sp,

                        fontWeight = FontWeight.Bold,

                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun SessionEarningDialog(
    language: String,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {

    var amount by remember {
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = onDismiss
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),

            shape = RoundedCornerShape(34.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 18.dp
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 28.dp,
                        vertical = 34.dp
                    ),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .background(
                            Color(0xFFFFF1EC),
                            CircleShape
                        ),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Orange,
                        modifier = Modifier.size(44.dp)
                    )
                }

                Spacer(modifier = Modifier.height(26.dp))

                Text(
                    text =
                        if(language == "kn")
                            "ಈ ಅವಧಿಯಲ್ಲಿ ನೀವು ಎಷ್ಟು ಗಳಿಸಿದ್ದೀರಿ?"
                        else
                            "How much did you earn this session?",

                    textAlign = TextAlign.Center,

                    color = Orange,

                    fontWeight = FontWeight.Bold,

                    fontSize = 28.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(

                    value = amount,

                    onValueChange = {
                        amount = it
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(86.dp),

                    textStyle = TextStyle(
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFB7A0),
                        textAlign = TextAlign.Center
                    ),

                    leadingIcon = {

                        Text(
                            text = "₹",
                            fontSize = 34.sp,
                            color = Color(0xFFFFD0C2),
                            fontWeight = FontWeight.Bold
                        )
                    },

                    singleLine = true,

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),

                    shape = RoundedCornerShape(24.dp),

                    colors = OutlinedTextFieldDefaults.colors(

                        focusedBorderColor = Color(0xFFF1EEE5),

                        unfocusedBorderColor = Color(0xFFF1EEE5),

                        focusedContainerColor = Color(0xFFF9F8F1),

                        unfocusedContainerColor = Color(0xFFF9F8F1)
                    )
                )

                Spacer(modifier = Modifier.height(34.dp))

                Button(

                    onClick = {

                        if(amount.isNotEmpty()) {

                            onConfirm(amount.toInt())
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp),

                    shape = RoundedCornerShape(24.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange
                    ),

                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 12.dp
                    )
                ) {

                    Text(
                        text =
                            if(language == "kn")
                                "ಸಲ್ಲಿಸಿ"
                            else
                                "Submit",

                        fontSize = 24.sp,

                        fontWeight = FontWeight.Bold,

                        color = Color.White
                    )
                }
            }
        }
    }
}

fun convertToKannadaNumbers(
    input: String
): String {

    val english = "0123456789"
    val kannada = "೦೧೨೩೪೫೬೭೮೯"

    var output = input

    english.forEachIndexed { index, c ->

        output =
            output.replace(
                c,
                kannada[index]
            )
    }

    return output
}