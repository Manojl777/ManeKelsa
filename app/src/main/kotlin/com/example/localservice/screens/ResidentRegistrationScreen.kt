package com.example.localservice.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private val ColorPrimary = Color(0xFF006D67)
private val ColorBackground = Color(0xFFF7F5EE)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResidentRegistrationScreen(
    language: String = "en",

    onLanguageChange: (String) -> Unit = {},

    darkMode: Boolean = false,

    onDarkModeChange: (Boolean) -> Unit = {},

    onBack: () -> Unit = {},

    onRegistrationComplete: () -> Unit = {}
) {

    val currentUser = Firebase.auth.currentUser
    val firestore = FirebaseFirestore.getInstance()
    val coroutineScope = rememberCoroutineScope()

    var fullName by remember {
        mutableStateOf(currentUser?.displayName ?: "")
    }

    var phone by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf(currentUser?.email ?: "")
    }

    var address1 by remember {
        mutableStateOf("")
    }

    var address2 by remember {
        mutableStateOf("")
    }

    var area by remember {
        mutableStateOf("")
    }

    var city by remember {
        mutableStateOf("")
    }

    var state by remember {
        mutableStateOf("")
    }

    var pincode by remember {
        mutableStateOf("")
    }

    var showSuccessDialog by remember {
        mutableStateOf(false)
    }

    if (showSuccessDialog) {

        Dialog(
            onDismissRequest = {}
        ) {

            Surface(
                shape = RoundedCornerShape(32.dp),
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(
                        horizontal = 28.dp,
                        vertical = 36.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Surface(
                        shape = RoundedCornerShape(100.dp),
                        color = Color(0xFFDDF5E5),
                        modifier = Modifier.size(120.dp)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF0BAA4B),
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = "Registered Successfully",
                        color = ColorPrimary,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Welcome to ManeKelsa",
                        color = Color.Gray,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = {
                            showSuccessDialog = false
                            onRegistrationComplete()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ColorPrimary
                        ),
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {

                        Text(
                            text = "Continue",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    Scaffold(
        containerColor = ColorBackground,

        topBar = {

            Surface(
                color = Color.White
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 14.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {

                        Surface(
                            color = ColorPrimary,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.size(46.dp)
                        ) {

                            Box(
                                contentAlignment = Alignment.Center
                            ) {

                                Icon(
                                    Icons.Default.People,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }

                        Text(
                            text = "Resident\nRegistration",
                            color = ColorPrimary,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp,
                            lineHeight = 24.sp,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            onDarkModeChange(!darkMode)
                        }
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

                    Surface(
                        modifier = Modifier.padding(horizontal = 6.dp),
                        shape = RoundedCornerShape(50),
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 2.dp
                    ) {

                        Text(
                            text =
                                if (language == "en")
                                    "ಕನ್ನಡ"
                                else
                                    "ENGLISH",

                            modifier = Modifier
                                .clickable {
                                    onLanguageChange(
                                        if (language == "en") "kn" else "en"
                                    )
                                }
                                .padding(
                                    horizontal = 14.dp,
                                    vertical = 8.dp
                                ),

                            color = ColorPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    IconButton(
                        onClick = onBack
                    ) {

                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp),

            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            Spacer(modifier = Modifier.height(6.dp))

            SectionTitle("Basic Details")

            CustomTextField(
                label = "Full Name",
                value = fullName,
                onValueChange = {
                    fullName = it
                }
            )

            CustomTextField(
                label = "Mobile Number",
                value = phone,
                onValueChange = {

                    val filtered = it.filter { char ->
                        char.isDigit()
                    }

                    if (filtered.length <= 10) {
                        phone = filtered
                    }
                }
            )

            OutlinedTextField(
                value = email,
                onValueChange = {},
                enabled = false,

                label = {
                    Text("Email ID")
                },

                leadingIcon = {

                    Icon(
                        Icons.Default.Email,
                        contentDescription = null
                    )
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(18.dp),

                singleLine = true,

                colors = androidx.compose.material3
                    .OutlinedTextFieldDefaults
                    .colors(
                        disabledContainerColor = Color.White,
                        disabledTextColor = Color.Gray,
                        disabledBorderColor = Color.LightGray
                    )
            )

            Spacer(modifier = Modifier.height(6.dp))

            SectionTitle("Address Details")

            CustomTextField(
                label = "Address Line 1",
                value = address1,
                onValueChange = {
                    address1 = it
                }
            )

            CustomTextField(
                label = "Address Line 2",
                value = address2,
                onValueChange = {
                    address2 = it
                }
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                CustomTextField(
                    label = "Area",
                    value = area,
                    modifier = Modifier.weight(1f),
                    onValueChange = {
                        area = it
                    }
                )

                CustomTextField(
                    label = "City",
                    value = city,
                    modifier = Modifier.weight(1f),
                    onValueChange = {
                        city = it
                    }
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                CustomTextField(
                    label = "State",
                    value = state,
                    modifier = Modifier.weight(1f),
                    onValueChange = {
                        state = it
                    }
                )

                CustomTextField(
                    label = "Pincode",
                    value = pincode,
                    modifier = Modifier.weight(1f),
                    onValueChange = {

                        pincode = it
                            .filter { char ->
                                char.isDigit()
                            }
                            .take(6)
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {

                    if (
                        fullName.isBlank() ||
                        phone.length != 10
                    ) {
                        return@Button
                    }

                    val uid =
                        currentUser?.uid
                            ?: return@Button

                    coroutineScope.launch {

                        val residentData = hashMapOf(
                            "fullName" to fullName,
                            "contactNumber" to phone,
                            "email" to email,
                            "addrLine1" to address1,
                            "addrLine2" to address2,
                            "area" to area,
                            "city" to city,
                            "state" to state,
                            "pincode" to pincode
                        )

                        firestore
                            .collection("residents")
                            .document(uid)
                            .set(residentData)
                            .await()

                        showSuccessDialog = true
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp),

                shape = RoundedCornerShape(22.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorPrimary
                )
            ) {

                Text(
                    text = "Complete Registration",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SectionTitle(
    title: String
) {

    Text(
        text = title,
        color = ColorPrimary,
        fontSize = 22.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier.padding(top = 10.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,

        label = {
            Text(label)
        },

        modifier = modifier
            .fillMaxWidth()
            .height(78.dp),

        singleLine = true,

        shape = RoundedCornerShape(18.dp),

        colors = androidx.compose.material3
            .OutlinedTextFieldDefaults
            .colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray
            )
    )
}