package com.example.localservice.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private val ColorPrimary = Color(0xFF006D67)
private val ColorBackground = Color(0xFFF8F6EF)

@Composable
fun ResUserMenu(

    expanded: Boolean,

    language: String = "en",

    onDismiss: () -> Unit,

    onMyAccount: () -> Unit,

    onSettings: () -> Unit,

    onSignOut: () -> Unit
){

    DropdownMenu(

        expanded = expanded,

        onDismissRequest = onDismiss,

        offset = androidx.compose.ui.unit.DpOffset(
            x = (-10).dp,
            y = 8.dp
        ),

        modifier = Modifier
            .width(230.dp)
            .background(Color.White),

        shape = RoundedCornerShape(22.dp)
    ) {

        DropdownMenuItem(

            text = {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = Color(0xFFFF5722)
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Text(
                        if (language == "en")
                            "My Account"
                        else
                            "ನನ್ನ ಖಾತೆ",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },

            onClick = {

                onDismiss()
                onMyAccount()
            }
        )

        DropdownMenuItem(

            text = {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.Settings,
                        contentDescription = null,
                        tint = Color(0xFFFF5722)
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Text(
                        if (language == "en")
                            "Settings"
                        else
                            "ಸೆಟ್ಟಿಂಗ್ಸ್",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },

            onClick = {

                onDismiss()
                onSettings()
            }
        )

        HorizontalDivider()

        DropdownMenuItem(

            text = {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.ExitToApp,
                        contentDescription = null,
                        tint = Color.Red
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Text(
                        if (language == "en")
                            "Sign Out"
                        else
                            "ಸೈನ್ ಔಟ್",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            },

            onClick = {

                FirebaseAuth.getInstance().signOut()

                onDismiss()

                onSignOut()
            }
        )
    }
}

@Composable
fun ResMyAccountScreen(

    language: String = "en",

    onLanguageChange: (String) -> Unit = {},

    darkMode: Boolean = false,

    onDarkModeToggle: () -> Unit = {},

    onBack: () -> Unit = {},

    onEditProfile: () -> Unit = {}

) {

    val firestore = FirebaseFirestore.getInstance()

    val currentUser = FirebaseAuth.getInstance().currentUser

    var userName by remember {
        mutableStateOf("")
    }

    var phone by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var address by remember {
        mutableStateOf("")
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

                phone =
                    it.getString("contactNumber") ?: ""

                email =
                    it.getString("email") ?: ""

                address =
                    "${it.getString("addrLine1") ?: ""}, " +
                            "${it.getString("addrLine2") ?: ""}, " +
                            "${it.getString("area") ?: ""}, " +
                            "${it.getString("city") ?: ""}, " +
                            "${it.getString("state") ?: ""} - " +
                            "${it.getString("pincode") ?: ""}"
            }
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
            .verticalScroll(rememberScrollState())
    ){

        TopBarSection(
            title = if (language == "en")
                "My Account"
            else
                "ನನ್ನ ಖಾತೆ",
            language = language,
            darkMode = darkMode,
            onDarkModeToggle = onDarkModeToggle,
            onBack = onBack,
            onLanguageChange = onLanguageChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),

            shape = RoundedCornerShape(34.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )

        ) {

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(26.dp),

                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Surface(

                    shape = CircleShape,

                    color = Color(0xFFFF5A1F),

                    modifier = Modifier.size(120.dp)

                ) {

                    Box(
                        contentAlignment = Alignment.Center
                    ) {

                        Text(

                            text = userName.firstOrNull()?.toString() ?: "M",

                            color = Color.White,

                            fontSize = 52.sp,

                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(

                    text = userName,

                    fontSize = 28.sp,

                    fontWeight = FontWeight.ExtraBold,

                    color = Color(0xFF4A4A4A)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(

                    text = if (language == "en")
                        "RESIDENT"
                    else
                        "ನಿವಾಸಿ",

                    color = Color.LightGray,

                    fontWeight = FontWeight.Bold,

                    letterSpacing = 3.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(

            text = if (language == "en")
                "BASIC INFORMATION"
            else
                "ಮೂಲಭೂತ ಮಾಹಿತಿ",

            modifier = Modifier.padding(start = 26.dp),

            color = Color.LightGray,

            fontWeight = FontWeight.Bold,

            letterSpacing = 3.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),

            shape = RoundedCornerShape(30.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )

        ) {

            Column {

                AccountRow(
                    title = "CONTACT NUMBER",
                    value = phone,
                    onEditClick = onEditProfile
                )

                HorizontalDivider()

                AccountRow(
                    title = "EMAIL ID",
                    value = email
                )

                HorizontalDivider()

                AccountRow(
                    title = "ADDRESS",
                    value = address
                )
            }
        }
    }
}

@Composable
fun AccountRow(

    title: String,

    value: String,
    onEditClick: () -> Unit = {}

) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp),

        verticalAlignment = Alignment.CenterVertically

    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(

                text = title,

                color = Color.LightGray,

                fontWeight = FontWeight.Bold,

                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(

                text = value,

                fontWeight = FontWeight.Bold,

                fontSize = 22.sp,

                color = Color(0xFF4B4B4B)
            )
        }

        Text(

            text = if (title.contains("ADDRESS"))
                "Edit"
            else
                "Edit",

            color = ColorPrimary,

            fontWeight = FontWeight.Bold,

            fontSize = 18.sp,

            modifier = Modifier.clickable {

                onEditClick()
            }
        )
    }
}

@Composable
fun ResSettingsScreen(

    language: String = "en",

    onLanguageChange: (String) -> Unit = {},

    darkMode: Boolean = false,

    onDarkModeToggle: () -> Unit = {},

    onBack: () -> Unit = {},

    onAccountDeleted: () -> Unit = {}

){


    val firestore = FirebaseFirestore.getInstance()

    val currentUser = FirebaseAuth.getInstance().currentUser

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    var deleteText by remember {
        mutableStateOf("")
    }

    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {},

            confirmButton = {

                Button(

                    onClick = {

                        val uid =
                            currentUser?.uid ?: return@Button

                        firestore
                            .collection("residents")
                            .document(uid)
                            .delete()

                        currentUser.delete()

                        FirebaseAuth.getInstance().signOut()

                        onAccountDeleted()
                    },

                    enabled = deleteText == "DELETE",

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFCDD2)
                    )

                ) {

                    Text(

                        if (language == "en")
                            "Yes, Delete"
                        else
                            "ಹೌದು, ಅಳಿಸಿ"
                    )
                }
            },

            dismissButton = {

                Button(

                    onClick = {

                        showDeleteDialog = false
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF8F6EF)
                    )

                ) {

                    Text(

                        if (language == "en")
                            "Cancel"
                        else
                            "ರದ್ದುಮಾಡಿ",
                        color = Color.Gray
                    )
                }
            },

            title = {

                Column(

                    modifier = Modifier.fillMaxWidth(),

                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    Surface(

                        shape = CircleShape,

                        color = Color(0xFFFFE3E3),

                        modifier = Modifier.size(100.dp)

                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                "!",
                                color = Color.Red,
                                fontSize = 52.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(

                        text = if (language == "en")
                            "Delete Account\nPermanently?"
                        else
                            "ಖಾತೆಯನ್ನು ಶಾಶ್ವತವಾಗಿ\nಅಳಿಸುವುದೇ?",

                        fontWeight = FontWeight.Bold,

                        fontSize = 28.sp,

                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            },

            text = {

                Column {

                    Text(

                        text = "To confirm deletion, please type DELETE below.",

                        color = Color.Gray,

                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    OutlinedTextField(

                        value = deleteText,

                        onValueChange = {

                            deleteText = it
                        },

                        placeholder = {

                            Text(
                                "Type DELETE"
                            )
                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(18.dp)
                    )
                }
            }
        )
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)

    ) {

        TopBarSection(
            title = if (language == "en")
                "Settings"
            else
                "ಸೆಟ್ಟಿಂಗ್ಸ್",
            language = language,
            darkMode = darkMode,
            onDarkModeToggle = onDarkModeToggle,
            onBack = onBack,
            onLanguageChange = onLanguageChange
        )

        Spacer(modifier = Modifier.height(26.dp))

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),

            shape = RoundedCornerShape(30.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )

        ) {

            Column {

                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onDarkModeToggle()
                        }
                        .padding(22.dp),

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Surface(

                        shape = RoundedCornerShape(18.dp),

                        color = Color(0xFFFFE8E0),

                        modifier = Modifier.size(54.dp)

                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(

                                Icons.Outlined.DarkMode,

                                contentDescription = null,

                                tint = Color(0xFFFF5722)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(

                        text = if (language == "en") {

                            if (darkMode)
                                "Switch to Light Mode"
                            else
                                "Switch to Dark Mode"

                        } else {

                            if (darkMode)
                                "ಲೈಟ್ ಮೋಡ್‌ಗೆ ಬದಲಿಸಿ"
                            else
                                "ಡಾರ್ಕ್ ಮೋಡ್‌ಗೆ ಬದಲಿಸಿ"
                        },

                        fontWeight = FontWeight.SemiBold,

                        fontSize = 22.sp
                    )
                }

                HorizontalDivider()

                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                            showDeleteDialog = true
                        }
                        .padding(22.dp),

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Surface(

                        shape = RoundedCornerShape(18.dp),

                        color = Color(0xFFFFE3E3),

                        modifier = Modifier.size(54.dp)

                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(

                                Icons.Default.Delete,

                                contentDescription = null,

                                tint = Color.Red
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(

                        text = if (language == "en")
                            "Delete Account"
                        else
                            "ಖಾತೆ ಅಳಿಸಿ",

                        color = Color.Red,

                        fontWeight = FontWeight.Bold,

                        fontSize = 22.sp,

                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        ">",
                        color = Color.LightGray,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TopBarSection(

    title: String,

    language: String,

    darkMode: Boolean,

    onDarkModeToggle: () -> Unit,

    onBack: () -> Unit,
    onLanguageChange: (String) -> Unit,


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

            text = title,

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
}