package com.example.localservice.screens

import android.R.attr.text
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.localservice.firebase.FirebaseManager
import com.example.localservice.model.WorkerModel
import com.example.localservice.repository.WorkerRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import com.example.localservice.i18n.t
import android.util.Base64
import java.io.InputStream

private data class WorkerServiceItem(
    val key: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private val BackgroundColor = Color(0xFFF7F4ED)
private val Orange = Color(0xFFFF5A1F)
private val TextGray = Color(0xFF8B8B8B)
private val BorderGray = Color(0xFFE5E2DB)
private val FieldColor = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerRegistrationScreen(
    language: String,
    onLanguageChange: (String) -> Unit,
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onNavigateWorkerDashboard: () -> Unit,
    onBack: () -> Unit
) {

    val context = LocalContext.current
    val tr: (String) -> String = {
        t(language, it)
    }
    val scope = rememberCoroutineScope()
    val workerRepository = remember { WorkerRepository() }

    val currentUser = Firebase.auth.currentUser
    val generatedWorkerId = remember {
        "MKW${(100000..999999).random()}"
    }

    var fullName by remember {
        mutableStateOf(currentUser?.displayName ?: "")
    }

    var email by remember {
        mutableStateOf(currentUser?.email ?: "")
    }

    var phone by remember { mutableStateOf("") }

    var gender by remember { mutableStateOf("") }

    var dob by remember { mutableStateOf("") }

    var servicesExpanded by remember { mutableStateOf(false) }

    val serviceOptions = remember {
        listOf(
            WorkerServiceItem("svcHouseCleaning", Icons.Default.CleaningServices),
            WorkerServiceItem("svcDeepCleaning", Icons.Default.CleaningServices),
            WorkerServiceItem("svcElectricalRepair", Icons.Default.Bolt),
            WorkerServiceItem("svcElectricalInstallation", Icons.Default.Bolt),
            WorkerServiceItem("svcPlumbing", Icons.Default.WaterDrop),
            WorkerServiceItem("svcApplianceRepair", Icons.Default.Handyman),
            WorkerServiceItem("svcACService", Icons.Default.AcUnit),
            WorkerServiceItem("svcPainting", Icons.Default.FormatPaint),
            WorkerServiceItem("svcCarpentry", Icons.Default.Handyman),
            WorkerServiceItem("svcGardening", Icons.Default.Yard),
            WorkerServiceItem("svcVehicleWash", Icons.Default.DirectionsCar),
            WorkerServiceItem("svcElderlyCare", Icons.Default.Person),
            WorkerServiceItem("svcChildCare", Icons.Default.ChildCare),
            WorkerServiceItem("svcCooking", Icons.Default.Restaurant),
            WorkerServiceItem("svcPetCare", Icons.Default.Pets)
        )
    }

    var selectedServices by remember {
        mutableStateOf(setOf<String>())
    }

    var address1 by remember { mutableStateOf("") }
    var address2 by remember { mutableStateOf("") }

    var area by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    var state by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }

    var workAreas by remember { mutableStateOf("") }

    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var idCardUri by remember { mutableStateOf<Uri?>(null) }

    var profileBase64 by remember {
        mutableStateOf("")
    }

    var idCardBase64 by remember {
        mutableStateOf("")
    }

    val profilePicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->

            if (uri != null && isValidImage(context, uri)) {
                profileImageUri = uri

                profileBase64 =
                    uriToBase64(context, uri)

                profileImageUri = uri
            } else {
                Toast.makeText(
                    context,
                    "Only JPG, JPEG, PNG under 10MB allowed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    val idPicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->

            if (uri != null && isValidImage(context, uri)) {
                idCardUri = uri

                idCardBase64 =
                    uriToBase64(context, uri)

                idCardUri = uri

            } else {
                Toast.makeText(
                    context,
                    "Only JPG, JPEG, PNG under 10MB allowed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    Scaffold(
        containerColor = BackgroundColor,
        topBar = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 18.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Orange),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Work,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text =
                            if (language == "en")
                                "ManeKelsa W"
                            else
                                "ಮನೆಕೆಲಸW",
                        fontWeight = FontWeight.Bold,
                        color = Orange,
                        fontSize = 24.sp
                    )
                }

                IconButton(
                    onClick = {
                        onDarkModeChange(!darkMode)
                    },
                    modifier = Modifier
                        .size(42.dp)
                        .border(
                            1.dp,
                            BorderGray,
                            CircleShape
                        )
                ) {

                    Icon(
                        if (darkMode)
                            Icons.Outlined.LightMode
                        else
                            Icons.Outlined.DarkMode,
                        contentDescription = null,
                        tint = Color(0xFF006D77)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color(0xFFF4F7F7),
                    border = BorderStroke(1.dp, BorderGray),
                    onClick = {
                        onLanguageChange(
                            if (language == "en") "kn" else "en"
                        )
                    }
                ) {

                    Text(
                        text =
                            if (language == "en")
                                "ಕನ್ನಡ"
                            else
                                "ENGLISH",
                        modifier = Modifier.padding(
                            horizontal = 18.dp,
                            vertical = 10.dp
                        ),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF006D77),
                        fontSize = 12.sp
                    )
                }

                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(BackgroundColor)
                .padding(20.dp)
        ) {

            WorkerSectionTitle(tr("personalDetails"))

            WorkerAppTextField(
                label = tr("fullName"),
                value = fullName,
                onValueChange = {
                    fullName = it
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            WorkerAppTextField(
                label = tr("contactNumber"),
                value = phone,
                keyboardType = KeyboardType.Phone,
                onValueChange = {

                    val filtered = it.filter { c ->
                        c.isDigit()
                    }

                    if (filtered.length <= 10) {
                        phone = filtered
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            WorkerAppDisabledField(
                label = "EMAIL",
                value = email
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                GenderDropdown(
                    language = language,
                    modifier = Modifier.weight(1f),
                    gender = gender,
                    onSelected = {
                        gender = it
                    }
                )

                DOBField(
                    language = language,
                    modifier = Modifier.weight(1f),
                    dob = dob,
                    onSelected = {
                        dob = it
                    }
                )
            }

            Text(
                text = "• Must be atleast 18 to register as worker",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(
                    top = 8.dp,
                    start = 4.dp
                )
            )

            Spacer(modifier = Modifier.height(26.dp))

            WorkerSectionTitle(tr("servicesProvided"))

            MultiSelectServices(
                language = language,
                options = serviceOptions,
                selected = selectedServices,
                expanded = servicesExpanded,
                onExpandedChange = {
                    servicesExpanded = it
                },
                onSelectionChange = {
                    selectedServices = it
                }
            )
            Spacer(modifier = Modifier.height(26.dp))

            WorkerSectionTitle(tr("addressDetails"))

            WorkerAppTextField(
                label = tr("addressLine1"),
                value = address1,
                onValueChange = {
                    address1 = it
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            WorkerAppTextField(
                label = tr("addressLine2"),
                value = address2,
                onValueChange = {
                    address2 = it
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                WorkerAppTextField(
                    modifier = Modifier.weight(1f),
                    label = tr("area"),
                    value = area,
                    onValueChange = {
                        area = it
                    }
                )

                WorkerAppTextField(
                    modifier = Modifier.weight(1f),
                    label = tr("city"),
                    value = city,
                    onValueChange = {
                        city = it
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                WorkerAppTextField(
                    modifier = Modifier.weight(1f),
                    label = tr("state"),
                    value = state,
                    onValueChange = {
                        state = it
                    }
                )

                WorkerAppTextField(
                    modifier = Modifier.weight(1f),
                    label = tr("pincode"),
                    value = pincode,
                    keyboardType = KeyboardType.Number,
                    onValueChange = {
                        pincode = it
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            WorkerAppTextField(
                label = tr("preferredWorkAreas"),
                value = workAreas,
                onValueChange = {
                    workAreas = it
                }
            )

            Spacer(modifier = Modifier.height(28.dp))

            WorkerSectionTitle(tr("identityCard"))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                UploadBox(
                    modifier = Modifier.weight(1f),
                    title = tr("profilePhoto"),
                    imageUri = profileImageUri,
                    onClick = {
                        profilePicker.launch("image/*")
                    }
                )

                UploadBox(
                    modifier = Modifier.weight(1f),
                    title = tr("idCard"),
                    imageUri = idCardUri,
                    onClick = {
                        idPicker.launch("image/*")
                    }
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = {

                    if (phone.length != 10) {
                        Toast.makeText(
                            context,
                            "Phone number must be exactly 10 digits",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@Button
                    }

                    val uid =
                        FirebaseManager.auth.currentUser?.uid
                            ?: return@Button

                    scope.launch {

                        val worker = WorkerModel(

                            uid = currentUser?.uid ?: "",

                            id = "MKW${(100000..999999).random()}",

                            fullName = fullName,

                            email = email,

                            contactNumber = phone,

                            gender = gender,

                            dob = dob,

                            serviceTypes = selectedServices.toList(),

                            addrLine1 = address1,

                            addrLine2 = address2,

                            area = area,

                            city = city,

                            state = state,

                            pincode = pincode,

                            preferredAreasText = workAreas,

                            profilePic = profileBase64,

                            idProof = idCardBase64,

                            minRate = 0,

                            isAvailable = false,

                            rating = 4.5,

                            totalRatings = 0,

                            totalEarnings = 0,

                            createdAt = System.currentTimeMillis().toString(),

                            updatedAt = System.currentTimeMillis().toString()
                        )

                        workerRepository.saveWorker(
                            uid,
                            worker
                        )

                        onNavigateWorkerDashboard()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange
                ),
                shape = RoundedCornerShape(22.dp)
            ) {

                Text(
                    text = tr("completeRegistration"),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun WorkerSectionTitle(text: String) {

    Text(
        text = text,
        color = Orange,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

    Spacer(modifier = Modifier.height(14.dp))
}

@Composable
private fun WorkerAppTextField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(74.dp),
        label = {
            Text(label)
        },
        shape = RoundedCornerShape(20.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = FieldColor,
            unfocusedContainerColor = FieldColor,
            focusedBorderColor = BorderGray,
            unfocusedBorderColor = BorderGray
        )
    )
}

@Composable
private fun WorkerAppDisabledField(
    label: String,
    value: String
) {

    OutlinedTextField(
        value = value,
        onValueChange = {},
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp),
        label = {
            Text(label)
        },
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            disabledContainerColor = Color(0xFFF1F1F1),
            disabledBorderColor = BorderGray,
            disabledTextColor = Color.Gray
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GenderDropdown(
    language: String,
    modifier: Modifier,
    gender: String,
    onSelected: (String) -> Unit
){
    val tr: (String) -> String = {
        t(language, it)
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = it
        },
        modifier = modifier
    ) {

        OutlinedTextField(
            value = gender,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .height(74.dp),
            label = {
                Text(tr("gender"))
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = FieldColor,
                unfocusedContainerColor = FieldColor,
                focusedBorderColor = BorderGray,
                unfocusedBorderColor = BorderGray
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            listOf(
                "male",
                "female",
                "other"
            ).forEach { item ->

                DropdownMenuItem(
                    text = {

                        Text(
                            when (language) {

                                "kn" -> {

                                    when (item) {

                                        "male" -> "ಪುರುಷ"
                                        "female" -> "ಮಹಿಳೆ"
                                        "other" -> "ಇತರೆ"

                                        else -> item
                                    }
                                }

                                else -> {

                                    when (item) {

                                        "male" -> "Male"
                                        "female" -> "Female"
                                        "other" -> "Other"

                                        else -> item
                                    }
                                }
                            }
                        )
                    },
                    onClick = {

                        onSelected(
                            when (language) {

                                "kn" -> {

                                    when (item) {

                                        "male" -> "ಪುರುಷ"
                                        "female" -> "ಮಹಿಳೆ"
                                        "other" -> "ಇತರೆ"

                                        else -> item
                                    }
                                }

                                else -> {

                                    when (item) {

                                        "male" -> "Male"
                                        "female" -> "Female"
                                        "other" -> "Other"

                                        else -> item
                                    }
                                }
                            }
                        )

                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DOBField(
    language: String,
    modifier: Modifier,
    dob: String,
    onSelected: (String) -> Unit
) {
    val tr: (String) -> String = {
        t(language, it)
    }

    var showPicker by remember {
        mutableStateOf(false)
    }

    if (showPicker) {

        val calendar = Calendar.getInstance()

        calendar.add(Calendar.YEAR, -18)

        val maxDate = calendar.timeInMillis

        val state = rememberDatePickerState(
            selectableDates = object : SelectableDates {

                override fun isSelectableDate(
                    utcTimeMillis: Long
                ): Boolean {

                    return utcTimeMillis <= maxDate
                }
            }
        )

        DatePickerDialog(
            onDismissRequest = {
                showPicker = false
            },
            confirmButton = {

                TextButton(
                    onClick = {

                        state.selectedDateMillis?.let {

                            val sdf =
                                SimpleDateFormat(
                                    "dd-MM-yyyy",
                                    Locale.getDefault()
                                )

                            onSelected(
                                sdf.format(Date(it))
                            )
                        }

                        showPicker = false
                    }
                ) {
                    Text("OK")
                }
            }
        ) {

            DatePicker(state = state)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                showPicker = true
            }
    ) {

        OutlinedTextField(
            value = dob,
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp),
            label = {
                Text(tr("dateOfBirth"))
            },
            trailingIcon = {
                Icon(
                    Icons.Default.CalendarToday,
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledContainerColor = FieldColor,
                disabledBorderColor = BorderGray,
                disabledTextColor = Color.Black
            )
        )
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MultiSelectServices(
    language: String,
    options: List<WorkerServiceItem>,
    selected: Set<String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onSelectionChange: (Set<String>) -> Unit
) {
    val tr: (String) -> String = {
        when (language) {

            "kn" -> {

                when (it) {

                    "svcHouseCleaning" -> "ಮನೆ ಸ್ವಚ್ಛಗೊಳಿಸುವಿಕೆ"
                    "svcDeepCleaning" -> "ಡೀಪ್ ಕ್ಲೀನಿಂಗ್"
                    "svcElectricalRepair" -> "ವಿದ್ಯುತ್ ದುರಸ್ತಿ"
                    "svcElectricalInstallation" -> "ವಿದ್ಯುತ್ ಸ್ಥಾಪನೆ"
                    "svcPlumbing" -> "ಪ್ಲಂಬಿಂಗ್"
                    "svcApplianceRepair" -> "ಉಪಕರಣ ದುರಸ್ತಿ"
                    "svcACService" -> "ಎಸಿ ಸೇವೆ"
                    "svcPainting" -> "ಪೇಂಟಿಂಗ್"
                    "svcCarpentry" -> "ಕಾರ್ಪೆಂಟ್ರಿ"
                    "svcGardening" -> "ತೋಟಗಾರಿಕೆ"
                    "svcVehicleWash" -> "ವಾಹನ ತೊಳೆಯುವುದು"
                    "svcElderlyCare" -> "ಹಿರಿಯರ ಆರೈಕೆ"
                    "svcChildCare" -> "ಮಕ್ಕಳ ಆರೈಕೆ"
                    "svcCooking" -> "ಅಡುಗೆ"
                    "svcPetCare" -> "ಪೆಟ್ ಕೇರ್"

                    else -> it
                }
            }

            else -> {

                when (it) {

                    "svcHouseCleaning" -> "House Cleaning"
                    "svcDeepCleaning" -> "Deep Cleaning"
                    "svcElectricalRepair" -> "Electrical Repair"
                    "svcElectricalInstallation" -> "Electrical Installation"
                    "svcPlumbing" -> "Plumbing"
                    "svcApplianceRepair" -> "Appliance Repair"
                    "svcACService" -> "AC Service"
                    "svcPainting" -> "Painting"
                    "svcCarpentry" -> "Carpentry"
                    "svcGardening" -> "Gardening"
                    "svcVehicleWash" -> "Vehicle Wash"
                    "svcElderlyCare" -> "Elderly Care"
                    "svcChildCare" -> "Child Care"
                    "svcCooking" -> "Cooking"
                    "svcPetCare" -> "Pet Care"

                    else -> it
                }
            }
        }
    }

    Column {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onExpandedChange(!expanded)
                },
            shape = RoundedCornerShape(22.dp),
            color = FieldColor,
            border = BorderStroke(
                1.dp,
                BorderGray
            )
        ) {

            Column(
                modifier = Modifier.padding(14.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    FlowRow(
                        modifier = Modifier.weight(1f)
                    ) {

                        selected.forEach {

                            ServiceChip(
                                text = tr(it),
                                onRemove = {
                                    onSelectionChange(
                                        selected - it
                                    )
                                }
                            )
                        }
                    }

                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }

                if (expanded) {

                    Spacer(modifier = Modifier.height(16.dp))

                    options.forEach { item ->

                        val isSelected =
                            selected.contains(item.key)

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {

                                    if (isSelected) {
                                        onSelectionChange(
                                            selected - item.key
                                        )
                                    } else {
                                        onSelectionChange(
                                            selected + item.key
                                        )
                                    }
                                },
                            shape = RoundedCornerShape(16.dp),
                            color =
                                if (isSelected)
                                    Color(0xFFFFF1EC)
                                else
                                    Color.Transparent
                        ) {

                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    item.icon,
                                    contentDescription = null,
                                    tint =
                                        if (isSelected)
                                            Orange
                                        else
                                            Color.Gray
                                )

                                Spacer(modifier = Modifier.width(14.dp))

                                Text(
                                    text = tr(item.key),
                                    color =
                                        if (isSelected)
                                            Orange
                                        else
                                            Color.Gray,
                                    fontWeight =
                                        if (isSelected)
                                            FontWeight.Bold
                                        else
                                            FontWeight.Normal
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                if (isSelected) {

                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Orange
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

@Composable
private fun ServiceChip(
    text: String,
    onRemove: () -> Unit
) {

    Surface(
        shape = RoundedCornerShape(50),
        color = Color(0xFFFFF1EC),
        border = BorderStroke(
            1.dp,
            Color(0xFFFFD6C7)
        ),
        modifier = Modifier.padding(
            end = 8.dp,
            bottom = 8.dp
        )
    ) {

        Row(
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 7.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = text,
                color = Orange,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.width(6.dp))

            Icon(
                Icons.Default.Close,
                contentDescription = null,
                tint = Orange,
                modifier = Modifier
                    .size(14.dp)
                    .clickable {
                        onRemove()
                    }
            )
        }
    }
}

@Composable
private fun UploadBox(
    modifier: Modifier,
    title: String,
    imageUri: Uri?,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .height(180.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .border(
                1.5.dp,
                BorderGray,
                RoundedCornerShape(24.dp)
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {

        if (imageUri != null) {

            AsyncImage(
                model = imageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

        } else {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    Icons.Default.CameraAlt,
                    contentDescription = null,
                    tint = TextGray,
                    modifier = Modifier.size(38.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = title,
                    color = TextGray,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "JPG, PNG • <10MB",
                    color = TextGray,
                    fontSize = 11.sp
                )
            }
        }
    }
}

private fun isValidImage(
    context: Context,
    uri: Uri
): Boolean {

    val type =
        context.contentResolver.getType(uri)

    val allowed =
        type == "image/jpeg" ||
                type == "image/jpg" ||
                type == "image/png"

    val size =
        context.contentResolver
            .openFileDescriptor(uri, "r")
            ?.statSize ?: 0L

    val sizeMb = size / (1024 * 1024)

    return allowed && sizeMb <= 10
}
private fun uriToBase64(
    context: Context,
    uri: Uri
): String {

    val inputStream: InputStream? =
        context.contentResolver.openInputStream(uri)

    val bytes =
        inputStream?.readBytes()

    return Base64.encodeToString(
        bytes,
        Base64.DEFAULT
    )
}