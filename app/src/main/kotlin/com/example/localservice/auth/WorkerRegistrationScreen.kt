package com.example.localservice.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.localservice.ui.ColorSecondary
import com.example.localservice.ui.ColorSurface
import com.example.localservice.ui.ColorTertiary
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import java.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.text.style.TextAlign

private data class ServiceOption(val key: String, val labelKey: String)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun WorkerRegistrationScreen(
    language: String,
    onLanguageChange: (String) -> Unit,
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    userRepository: UserRepository,
    onNavigateHome: () -> Unit,
    onBack: () -> Unit,
) {
    val tr: (String) -> String = { t(language, it) }
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var genderExpanded by remember { mutableStateOf(false) }
    var gender by remember { mutableStateOf("male") }
    var dob by remember { mutableStateOf("") }
    val serviceOptions = remember {
        listOf(
            ServiceOption("house_cleaning", "svcHouseCleaning"),
            ServiceOption("deep_cleaning", "svcDeepCleaning"),
            ServiceOption("electrical_repair", "svcElectricalRepair"),
            ServiceOption("electric_installation", "svcElectricInstallation"),
            ServiceOption("plumbing", "svcPlumbing"),
            ServiceOption("appliance_repair", "svcApplianceRepair"),
            ServiceOption("ac_service", "svcACService"),
            ServiceOption("painting", "svcPainting"),
            ServiceOption("carpentry", "svcCarpentry"),
            ServiceOption("gardening", "svcGardening"),
            ServiceOption("vehicle_wash", "svcVehicleWash"),
            ServiceOption("elderly_care", "svcElderlyCare"),
            ServiceOption("child_care", "svcChildCare"),
            ServiceOption("cooking", "svcCooking"),
            ServiceOption("pet_care", "svcPetCare"),
        )
    }
    var selectedServices by remember { mutableStateOf(setOf<String>()) }
    var address1 by remember { mutableStateOf("") }
    var address2 by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var workAreas by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }
    val currentUid = "test_worker_uid"

    fun genderLabel(code: String) = when (code) {
        "male" -> tr("genderMale")
        "female" -> tr("genderFemale")
        else -> tr("genderOther")
    }

    if (showSuccess) {
        WorkerSuccessScreen(
            fullName = fullName,
            phone = phone,
            uid = currentUid,
            services = selectedServices.map { code -> serviceOptions.find { it.key == code }?.labelKey?.let { tr(it) } ?: code },
            workAreas = workAreas,
            onContinue = {
                showSuccess = false
                onNavigateHome()
            },
            language = language
        )
        return
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
                            color = ColorSecondary,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Work, contentDescription = null, tint = ColorSurface)
                            }
                        }
                        Column(modifier = Modifier.padding(start = 10.dp)) {
                            Text(tr("appName"), fontWeight = FontWeight.Bold, color = ColorSecondary, fontSize = 16.sp)
                            Text("W", fontWeight = FontWeight.Black, color = ColorSecondary, fontSize = 22.sp)
                        }
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
            SectionTitle(tr("secPersonal"), ColorPrimary)
            OutlinedField(tr("labelFullName"), fullName) { fullName = it }
            OutlinedField(tr("labelContact"), phone) { phone = it }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ExposedDropdownMenuBox(
                    expanded = genderExpanded,
                    onExpandedChange = { genderExpanded = it },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = genderLabel(gender),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(tr("labelGender"), color = ColorTertiary.copy(alpha=0.6f)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = ColorSurface,
                            unfocusedContainerColor = ColorSurface,
                            focusedBorderColor = ColorTertiary.copy(alpha=0.2f),
                            unfocusedBorderColor = ColorTertiary.copy(alpha=0.2f),
                            focusedTextColor = ColorTertiary,
                            unfocusedTextColor = ColorTertiary
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = genderExpanded,
                        onDismissRequest = { genderExpanded = false },
                        modifier = Modifier.background(ColorSurface)
                    ) {
                        listOf("male", "female", "other").forEach { code ->
                            DropdownMenuItem(
                                text = { Text(genderLabel(code), color = ColorTertiary) },
                                onClick = {
                                    gender = code
                                    genderExpanded = false
                                }
                            )
                        }
                    }
                }

                var showDatePicker by remember { mutableStateOf(false) }
                if (showDatePicker) {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.YEAR, -18)
                    val maxTimestamp = calendar.timeInMillis
                    val datePickerState = rememberDatePickerState(
                        selectableDates = object : SelectableDates {
                            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                                return utcTimeMillis <= maxTimestamp
                            }
                        }
                    )
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(onClick = {
                                datePickerState.selectedDateMillis?.let {
                                    val sdf = java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
                                    dob = sdf.format(java.util.Date(it))
                                }
                                showDatePicker = false
                            }) {
                                Text(tr("ok"))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = false }) {
                                Text(tr("cancel"))
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
                Surface(
                    onClick = { showDatePicker = true },
                    shape = RoundedCornerShape(16.dp),
                    color = ColorSurface,
                    border = BorderStroke(1.dp, ColorTertiary.copy(alpha = 0.2f)),
                    modifier = Modifier.weight(1f).height(64.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            if (dob.isEmpty()) {
                                Text(tr("labelDob"), color = ColorTertiary.copy(alpha = 0.6f))
                            } else {
                                Text(tr("labelDob"), fontSize = 10.sp, color = ColorTertiary.copy(alpha = 0.6f))
                                Text(dob, color = ColorTertiary)
                            }
                        }
                        Icon(Icons.Default.CalendarToday, contentDescription = null, tint = ColorTertiary.copy(alpha = 0.6f))
                    }
                }
            }

            SectionTitle(tr("secServices"), ColorPrimary)
            MultiSelectDropdown(
                label = tr("selectServices"),
                options = serviceOptions,
                selectedKeys = selectedServices,
                onSelectionChange = { selectedServices = it },
                language = language
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
            OutlinedField(tr("labelWorkAreas"), workAreas) { workAreas = it }

            SectionTitle(tr("secIdentity"), ColorPrimary)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(140.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = ColorSurface,
                    border = BorderStroke(1.dp, ColorTertiary.copy(alpha = 0.1f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = ColorTertiary.copy(alpha = 0.8f), modifier = Modifier.size(100.dp))
                    }
                }
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(140.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = ColorSurface,
                    border = BorderStroke(1.dp, ColorTertiary.copy(alpha = 0.1f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Icon(Icons.Default.Badge, contentDescription = null, tint = ColorTertiary.copy(alpha = 0.8f), modifier = Modifier.size(64.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("JPEG, PNG, JPG", fontSize = 10.sp, color = ColorTertiary.copy(alpha = 0.5f), fontWeight = FontWeight.Bold)
                            Text("< 15MB", fontSize = 10.sp, color = ColorTertiary.copy(alpha = 0.5f), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (selectedServices.isEmpty() || fullName.isBlank() || phone.isBlank()) return@Button
                    // Bypassing Firebase calls for testing
                    showSuccess = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary)
            ) {
                Text(tr("completeRegistration"), fontWeight = FontWeight.Bold, color = ColorSurface, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SectionTitle(text: String, color: androidx.compose.ui.graphics.Color) {
    Text(text, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = color, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
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
        modifier = modifier.fillMaxWidth(),
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun MultiSelectDropdown(
    label: String,
    options: List<ServiceOption>,
    selectedKeys: Set<String>,
    onSelectionChange: (Set<String>) -> Unit,
    language: String
) {
    var expanded by remember { mutableStateOf(false) }
    val tr: (String) -> String = { t(language, it) }

    Column {
        Text(label.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ColorTertiary.copy(alpha = 0.5f), modifier = Modifier.padding(bottom = 8.dp, start = 4.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            Surface(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 56.dp),
                shape = RoundedCornerShape(16.dp),
                color = ColorSurface,
                border = BorderStroke(1.dp, ColorTertiary.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (selectedKeys.isEmpty()) {
                        Text("", color = ColorTertiary.copy(alpha = 0.5f), modifier = Modifier.weight(1f))
                    } else {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            selectedKeys.forEach { key ->
                                val opt = options.find { it.key == key }
                                Surface(
                                    color = ColorSecondary.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(50),
                                    border = BorderStroke(1.dp, ColorSecondary.copy(alpha = 0.2f))
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
                                        Text(opt?.labelKey?.let { tr(it) } ?: key, color = ColorSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            Icons.Default.Close, 
                                            contentDescription = "Remove", 
                                            tint = ColorSecondary, 
                                            modifier = Modifier.size(12.dp).clickable {
                                                onSelectionChange(selectedKeys - key)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            }
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.background(ColorSurface)) {
                options.forEach { opt ->
                    DropdownMenuItem(
                        text = { Text(tr(opt.labelKey), color = ColorTertiary) },
                        onClick = {
                            if (opt.key in selectedKeys) {
                                onSelectionChange(selectedKeys - opt.key)
                            } else {
                                onSelectionChange(selectedKeys + opt.key)
                            }
                        },
                        trailingIcon = {
                            if (opt.key in selectedKeys) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = ColorSecondary)
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WorkerSuccessScreen(
    fullName: String,
    phone: String,
    uid: String,
    services: List<String>,
    workAreas: String,
    onContinue: () -> Unit,
    language: String
) {
    val tr: (String) -> String = { t(language, it) }
    
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Successfully Registered as Worker",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = ColorSurface,
                modifier = Modifier.padding(bottom = 24.dp),
                textAlign = TextAlign.Center
            )
            
            androidx.compose.material3.Card(
            shape = RoundedCornerShape(24.dp),
            colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = ColorSurface),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(ColorPrimary)
                )
                
                Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxWidth()) {
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = ColorSurface,
                        shadowElevation = 8.dp,
                        modifier = Modifier
                            .size(120.dp)
                            .offset(y = (-60).dp)
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.padding(16.dp).fillMaxSize(), tint = ColorPrimary)
                    }
                    
                    Column(
                        modifier = Modifier.padding(top = 70.dp, start = 24.dp, end = 24.dp, bottom = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(fullName, fontSize = 28.sp, fontWeight = FontWeight.Black, color = ColorTertiary)
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)) {
                            services.forEach {
                                Surface(color = ColorSecondary.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                                    Text(it.uppercase(), color = ColorSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Surface(
                            color = ColorAppBg,
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, ColorTertiary.copy(alpha = 0.1f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(24.dp)
                            ) {
                                Text(tr("labelContact").uppercase(), fontSize = 10.sp, color = ColorTertiary.copy(alpha = 0.6f), fontWeight = FontWeight.Bold)
                                Text(phone, fontSize = 20.sp, fontWeight = FontWeight.Black, color = ColorTertiary, modifier = Modifier.padding(top = 4.dp))
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(tr("workerId").uppercase(), fontSize = 10.sp, color = ColorTertiary.copy(alpha = 0.6f), fontWeight = FontWeight.Bold)
                                Text("MKW-${uid.take(6).uppercase()}", fontSize = 24.sp, fontWeight = FontWeight.Black, color = ColorPrimary, modifier = Modifier.padding(top = 4.dp))
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        val areaList = workAreas.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                        if (areaList.isNotEmpty()) {
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                areaList.forEach {
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = ColorSurface,
                                        border = BorderStroke(1.dp, ColorPrimary.copy(alpha = 0.2f))
                                    ) {
                                        Text(it, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ColorPrimary, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                                    }
                                }
                            }
                        }
                    }
                }
                
                Surface(
                    color = ColorTertiary.copy(alpha = 0.05f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = androidx.compose.ui.graphics.Color(0xFF22C55E), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(tr("verifiedProfessional"), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ColorTertiary.copy(alpha = 0.8f))
                        }
                        Text("#${uid.take(6).uppercase()}", fontSize = 12.sp, color = ColorTertiary.copy(alpha = 0.5f))
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = onContinue,
            shape = RoundedCornerShape(16.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = ColorSecondary),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(tr("continueDashboard"), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = ColorSurface)
        }
    }
    }
}
