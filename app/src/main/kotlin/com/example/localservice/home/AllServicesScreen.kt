package com.example.localservice.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Elderly
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SoupKitchen
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localservice.components.GlassCard
import com.example.localservice.components.PremiumBackgroundBlobs
import com.example.localservice.components.ServiceChip
import com.example.localservice.i18n.t
import com.example.localservice.ui.*

@Composable
fun AllServicesScreen(
    language: String
) {

    val tr: (String) -> String = { t(language, it) }

    var searchText by remember {
        mutableStateOf("")
    }

    val services = listOf(
        ServiceItem("svcHouseCleaning", Icons.Default.CleaningServices, ServiceBlue),
        ServiceItem("svcDeepCleaning", Icons.Default.AutoAwesome, ServicePurple),
        ServiceItem("svcElectricalRepair", Icons.Default.Bolt, ServiceYellow),
        ServiceItem("svcElectricInstallation", Icons.Default.ElectricBolt, ServiceOrange),
        ServiceItem("svcPlumbing", Icons.Default.WaterDrop, ServiceCyan),
        ServiceItem("svcApplianceRepair", Icons.Default.Handyman, ServiceRed),
        ServiceItem("svcACService", Icons.Default.Air, ServiceBlue),
        ServiceItem("svcPainting", Icons.Default.FormatPaint, ServicePink),
        ServiceItem("svcCarpentry", Icons.Default.Construction, ServiceBrown),
        ServiceItem("svcGardening", Icons.Default.Grass, ServiceEmerald),
        ServiceItem("svcVehicleWash", Icons.Default.DirectionsCar, ServiceBlue),
        ServiceItem("svcElderlyCare", Icons.Default.Elderly, ServicePurple),
        ServiceItem("svcChildCare", Icons.Default.ChildCare, ServicePink),
        ServiceItem("svcCooking", Icons.Default.SoupKitchen, ServiceOrange),
        ServiceItem("svcPetCare", Icons.Default.Pets, ServiceYellow)
    )

    val filteredServices = services.filter {
        tr(it.nameKey).contains(searchText, ignoreCase = true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorAppBg)
    ) {

        PremiumBackgroundBlobs()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = ScreenHorizontalPadding)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "All Services",
                fontSize = 30.sp,
                fontWeight = FontWeight.Black,
                color = ColorPrimary,
                letterSpacing = (-1).sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Choose the service you need today",
                fontSize = 14.sp,
                color = ColorTertiary.copy(alpha = 0.6f),
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = ColorPrimary
                    )
                },
                placeholder = {
                    Text(
                        text = "Search services...",
                        color = ColorTertiary.copy(alpha = 0.4f)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = PillShape,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = ColorSurface,
                    unfocusedContainerColor = ColorSurface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                ServiceChip(
                    text = "Cleaning",
                    icon = Icons.Default.AutoAwesome,
                    color = ServiceBlue
                )

                ServiceChip(
                    text = "Repair",
                    icon = Icons.Default.Handyman,
                    color = ServiceOrange
                )

                ServiceChip(
                    text = "Care",
                    icon = Icons.Default.Pets,
                    color = ServicePurple
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(filteredServices) { service ->

                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { }
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Surface(
                                shape = CircleShape,
                                color = service.color.copy(alpha = 0.12f),
                                modifier = Modifier.size(68.dp)
                            ) {

                                Box(
                                    contentAlignment = Alignment.Center
                                ) {

                                    Icon(
                                        imageVector = service.icon,
                                        contentDescription = null,
                                        tint = service.color,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(18.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = tr(service.nameKey),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = ColorTertiary
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "Professional verified workers available",
                                    fontSize = 12.sp,
                                    color = ColorTertiary.copy(alpha = 0.55f),
                                    lineHeight = 18.sp
                                )
                            }

                            Surface(
                                shape = CircleShape,
                                color = service.color.copy(alpha = 0.12f),
                                modifier = Modifier.size(36.dp)
                            ) {

                                Box(
                                    contentAlignment = Alignment.Center
                                ) {

                                    Icon(
                                        Icons.Default.ArrowForwardIos,
                                        contentDescription = null,
                                        tint = service.color,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}