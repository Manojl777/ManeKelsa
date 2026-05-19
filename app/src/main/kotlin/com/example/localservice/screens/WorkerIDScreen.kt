package com.example.localservice.screens

import android.util.Base64
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.localservice.i18n.t

private val BgColor = Color(0xFFF7F4ED)
private val Teal = Color(0xFF006D6F)
private val Orange = Color(0xFFFF5A1F)

@Composable
fun WorkerIDScreen(
    language: String,
    fullName: String,
    phone: String,
    services: List<String>,
    workAreas: List<String>,
    profileImage: String,
    workerUniqueId: String,
    onEnterDashboard: () -> Unit
) {

    val tr: (String) -> String = {
        t(language, it)
    }

    WorkerIDCard(
        tr = tr,
        fullName = fullName,
        phone = phone,
        services = services,
        workAreas = workAreas,
        profileImage = profileImage,
        workerUniqueId = workerUniqueId,
        onEnterDashboard = onEnterDashboard
    )
}

@Composable
private fun WorkerIDCard(
    tr: (String) -> String,
    fullName: String,
    phone: String,
    services: List<String>,
    workAreas: List<String>,
    profileImage: String,
    workerUniqueId: String,
    onEnterDashboard: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = tr("professionalId"),
            color = Teal,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = tr("workerIdSubtitle"),
            color = Color.Gray,
            fontSize = 17.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(26.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(40.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .background(Teal)
                )

                Box(
                    modifier = Modifier.offset(y = (-95).dp),
                    contentAlignment = Alignment.Center
                ) {

                    Card(
                        modifier = Modifier.size(150.dp),
                        shape = RoundedCornerShape(34.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        )
                    ) {

                        if (profileImage.isNotEmpty()) {

                            AsyncImage(

                                model = Base64.decode(
                                    profileImage,
                                    Base64.DEFAULT
                                ),

                                contentDescription = null,

                                modifier = Modifier.fillMaxSize(),

                                contentScale = ContentScale.Crop
                            )

                        } else {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xFFF2F2F2)),
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = "No Image",
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .offset(y = (-60).dp)
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = fullName,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5B5B54),
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        services.take(2).forEach {

                            Surface(
                                shape = RoundedCornerShape(50),
                                color = Color(0xFFFFF0EB)
                            ) {

                                Text(
                                    text = when (it) {

                                        "svcHouseCleaning" -> "HOUSE CLEANING"

                                        "svcDeepCleaning" -> "DEEP CLEANING"

                                        "svcElectricalRepair" -> "ELECTRICAL REPAIR"

                                        "svcElectricalInstallation" -> "ELECTRICAL INSTALLATION"

                                        "svcPlumbing" -> "PLUMBING"

                                        "svcApplianceRepair" -> "APPLIANCE REPAIR"

                                        "svcACService" -> "AC SERVICE"

                                        "svcPainting" -> "PAINTING"

                                        "svcCarpentry" -> "CARPENTRY"

                                        "svcGardening" -> "GARDENING"

                                        "svcVehicleWash" -> "VEHICLE WASH"

                                        "svcElderlyCare" -> "ELDERLY CARE"

                                        "svcChildCare" -> "CHILD CARE"

                                        "svcCooking" -> "COOKING"

                                        "svcPetCare" -> "PET CARE"

                                        else -> it.uppercase()
                                    },
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 6.dp
                                    ),
                                    color = Orange,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF8F7F2)
                        ),
                        border = BorderStroke(
                            1.dp,
                            Color(0xFFEEEEEE)
                        )
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 36.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = tr("contact").uppercase(),
                                color = Color.LightGray,
                                letterSpacing = 3.sp,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = phone,
                                color = Teal,
                                fontWeight = FontWeight.Bold,
                                fontSize = 26.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Divider(
                                modifier = Modifier.width(80.dp),
                                color = Color(0xFFE7E7E7)
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = tr("workerId").uppercase(),
                                color = Color.LightGray,
                                letterSpacing = 4.sp,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = workerUniqueId,
                                color = Teal,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp,
                                fontSize = 28.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(120.dp),
                        modifier = Modifier.height(60.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        items(workAreas) {

                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Color(0xFFF5FAFA),
                                border = BorderStroke(
                                    1.dp,
                                    Color(0xFFD4E8E8)
                                )
                            ) {

                                Text(
                                    text = it,
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 10.dp
                                    ),
                                    color = Teal,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 22.dp,
                            vertical = 20.dp
                        ),

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF14AE5C)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = tr("verifiedProfessional"),
                        color = Color.Gray,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "#${workerUniqueId.takeLast(6)}",
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onEnterDashboard,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .height(68.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange
                ),
                shape = RoundedCornerShape(24.dp)
            ) {

                Text(
                    text = tr("enterDashboard"),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    fontSize = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}