package com.example.localservice.auth

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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.People
import androidx.compose.ui.window.Dialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localservice.data.UserRepository
import com.example.localservice.i18n.t
import com.example.localservice.ui.ColorAppBg
import com.example.localservice.ui.ColorPrimary
import com.example.localservice.ui.ColorSurface
import com.example.localservice.ui.ColorTertiary
import com.example.localservice.repository.ResidentRepository
import com.example.localservice.firebase.FirebaseManager
import com.example.localservice.model.ResidentModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResidentRegistrationScreen(
    language: String,
    onLanguageChange: (String) -> Unit,
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    userRepository: UserRepository, // Keep for signature compatibility
    onNavigateHome: () -> Unit,
    onBack: () -> Unit,
) {
    val tr: (String) -> String = { t(language, it) }
    val residentRepository = remember { ResidentRepository() }
    val coroutineScope = rememberCoroutineScope()
    val currentUser = Firebase.auth.currentUser

    var fullName by remember {
        mutableStateOf(
            currentUser?.displayName ?: ""
        )
    }
    var phone by remember { mutableStateOf("") }
    var email by remember {
        mutableStateOf(
            currentUser?.email ?: ""
        )
    }
    var address1 by remember { mutableStateOf("") }
    var address2 by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }

    if (showSuccess) {
        Dialog(
            onDismissRequest = { },
            properties = androidx.compose.ui.window.DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = ColorSurface,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = ColorPrimary, modifier = Modifier.size(64.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Registered Successfully", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = ColorPrimary)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            showSuccess = false
                            onNavigateHome()
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary)
                    ) {
                        Text(tr("continueDashboard"), fontWeight = FontWeight.Bold, color = ColorSurface)
                    }
                }
            }
        }
    }

    Scaffold(
        containerColor = ColorAppBg,
        topBar = {
            Surface(color = ColorAppBg) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            color = ColorPrimary,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.People, contentDescription = null, tint = ColorSurface)
                            }
                        }
                        Text(
                            "Complete\nRegistration",
                            fontWeight = FontWeight.ExtraBold,
                            color = ColorPrimary,
                            fontSize = 18.sp,
                            lineHeight = 22.sp,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                    IconButton(onClick = { onDarkModeChange(!darkMode) }) {
                        Icon(
                            if (darkMode) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                            contentDescription = null,
                            tint = ColorPrimary
                        )
                    }
                    Surface(
                        onClick = { onLanguageChange(if (language == "en") "kn" else "en") },
                        shape = RoundedCornerShape(50),
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 1.dp
                    ) {
                        Text(
                            if (language == "en") tr("langEnglish") else tr("langKannada"),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            fontWeight = FontWeight.SemiBold,
                            color = ColorPrimary,
                            fontSize = 11.sp
                        )
                    }
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = ColorTertiary.copy(alpha = 0.5f))
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SectionTitle(tr("secBasic"), ColorPrimary)
            OutlinedField(tr("labelFullName"), fullName) { fullName = it }
            OutlinedField(tr("labelContact"), phone) { phone = it }
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(tr("labelEmail"), color = ColorTertiary.copy(alpha=0.6f)) },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = ColorTertiary.copy(alpha=0.4f)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = ColorSurface,
                    unfocusedContainerColor = ColorSurface,
                    focusedBorderColor = ColorTertiary.copy(alpha=0.2f),
                    unfocusedBorderColor = ColorTertiary.copy(alpha=0.2f),
                    focusedTextColor = ColorTertiary,
                    unfocusedTextColor = ColorTertiary
                )
            )

            SectionTitle(tr("secAddress"), ColorPrimary)
            OutlinedField(tr("labelAddress1"), address1) { address1 = it }
            OutlinedField(tr("labelAddress2"), address2) { address2 = it }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedField(tr("labelArea"), area, Modifier.weight(1f)) { area = it }
                OutlinedField(tr("labelCity"), city, Modifier.weight(1f)) { city = it }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedField(tr("labelState"), state, Modifier.weight(1f)) { state = it }
                OutlinedField(tr("labelPincode"), pincode, Modifier.weight(1f)) { pincode = it }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (fullName.isBlank() || phone.isBlank()) return@Button
                    val uid = FirebaseManager.auth.currentUser?.uid ?: return@Button

                    coroutineScope.launch {

                        val resident = ResidentModel(
                            fullName = fullName,
                            contactNumber = phone,
                            email = email,
                            addrLine1 = address1,
                            addrLine2 = address2,
                            area = area,
                            city = city,
                            state = state,
                            pincode = pincode
                        )

                        residentRepository.saveResident(
                            uid,
                            resident
                        )

                        showSuccess = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                shape = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary)
            ) {
                Text(tr("completeRegistration"), fontWeight = FontWeight.Bold, color = ColorSurface, fontSize = 22.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SectionTitle(
    text: String,
    color: androidx.compose.ui.graphics.Color
) {

    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        letterSpacing = 2.sp,
        color = color,
        modifier = Modifier.padding(
            top = 14.dp,
            bottom = 10.dp
        )
    )
}
@Composable
private fun OutlinedField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label, color = ColorTertiary.copy(alpha=0.6f)) },
        modifier = modifier
            .fillMaxWidth()
            .height(78.dp),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
            focusedContainerColor = ColorSurface,
            unfocusedContainerColor = ColorSurface,
            focusedBorderColor = ColorTertiary.copy(alpha=0.2f),
            unfocusedBorderColor = ColorTertiary.copy(alpha=0.2f),
            focusedTextColor = ColorTertiary,
            unfocusedTextColor = ColorTertiary
        )
    )
}
