package com.example.localservice.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.localservice.components.PremiumBackgroundBlobs
import com.example.localservice.data.UserProfile
import com.example.localservice.i18n.t
import com.example.localservice.ui.*

@Composable
fun ServiceWorkersScreen(
    language: String,
    serviceName: String,
    onWorkerClick: (String) -> Unit
) {

    val tr: (String) -> String = { t(language, it) }

    val workers = remember {

        listOf(

            UserProfile(
                uid = "worker1",
                phone = "+919876543210",
                role = "worker",
                isOnline = true,
                fullName = "ನೀಲಾ ಹೆಗಡೆ",
                services = listOf("svcChildCare", "svcElderlyCare"),
                workAreas = "Rajajinagar",
                minRate = "480",
                ratingSum = 49,
                ratingCount = 10,
                totalEarnings = 8500
            ),

            UserProfile(
                uid = "worker2",
                phone = "+919876543211",
                role = "worker",
                isOnline = true,
                fullName = "ಸುರೇಶ್ ರೈನಾ",
                services = listOf("svcElectricalRepair", "svcPlumbing"),
                workAreas = "Yelahanka",
                minRate = "690",
                ratingSum = 46,
                ratingCount = 10,
                totalEarnings = 9200
            ),

            UserProfile(
                uid = "worker3",
                phone = "+919876543212",
                role = "worker",
                isOnline = true,
                fullName = "ಲತಾ ಮಂಗೇಶ್",
                services = listOf("svcCooking", "svcElderlyCare"),
                workAreas = "Jayanagar",
                minRate = "890",
                ratingSum = 44,
                ratingCount = 10,
                totalEarnings = 12000
            ),

            UserProfile(
                uid = "worker4",
                phone = "+919876543213",
                role = "worker",
                isOnline = true,
                fullName = "ಮೀನಾಕುಮಾರ್",
                services = listOf("svcApplianceRepair"),
                workAreas = "Indiranagar",
                minRate = "900",
                ratingSum = 48,
                ratingCount = 10,
                totalEarnings = 11000
            )
        )
    }

    val filteredWorkers = workers.filter {
        it.isOnline && it.services.contains(serviceName)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorAppBg)
    ) {

        PremiumBackgroundBlobs()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            item {

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = tr(serviceName),
                    style = MaterialTheme.typography.displaySmall,
                    color = ColorPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Verified professionals available near you",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ColorTertiary.copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "${filteredWorkers.size} workers available",
                    style = MaterialTheme.typography.labelLarge,
                    color = ColorSecondary
                )
            }

            items(filteredWorkers) { worker ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onWorkerClick(worker.uid)
                        },
                    shape = RoundedCornerShape(30.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = ColorSurface
                    ),
                    border = BorderStroke(
                        1.dp,
                        ColorTertiary.copy(alpha = 0.05f)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = Modifier.size(72.dp)
                            ) {

                                Surface(
                                    shape = RoundedCornerShape(24.dp),
                                    color = ColorPrimary.copy(alpha = 0.10f),
                                    modifier = Modifier.fillMaxSize()
                                ) {

                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {

                                        Text(
                                            text = worker.fullName?.first()?.toString() ?: "W",
                                            style = MaterialTheme.typography.headlineLarge,
                                            color = ColorPrimary
                                        )
                                    }
                                }

                                Surface(
                                    shape = CircleShape,
                                    color = Color(0xFF22C55E),
                                    modifier = Modifier
                                        .size(18.dp)
                                        .align(Alignment.BottomEnd)
                                ) {}
                            }

                            Spacer(modifier = Modifier.width(18.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = worker.fullName ?: "Worker",
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = ColorTertiary
                                    )

                                    Spacer(modifier = Modifier.width(6.dp))

                                    Icon(
                                        Icons.Default.Verified,
                                        contentDescription = null,
                                        tint = ColorPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = worker.workAreas ?: "Bangalore",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = ColorTertiary.copy(alpha = 0.55f)
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Icon(
                                        Icons.Default.ThumbUp,
                                        contentDescription = null,
                                        tint = Color(0xFF22C55E),
                                        modifier = Modifier.size(15.dp)
                                    )

                                    Spacer(modifier = Modifier.width(5.dp))

                                    Text(
                                        text = "4.9 Rating",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = ColorTertiary
                                    )

                                    Spacer(modifier = Modifier.width(14.dp))

                                    Text(
                                        text = "₹${worker.minRate}",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = ColorPrimary
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = ColorSecondary.copy(alpha = 0.12f),
                                modifier = Modifier.size(54.dp)
                            ) {

                                Box(
                                    contentAlignment = Alignment.Center
                                ) {

                                    Icon(
                                        Icons.Default.Phone,
                                        contentDescription = null,
                                        tint = ColorSecondary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Experienced professional with verified profile and high customer satisfaction.",
                            style = MaterialTheme.typography.bodySmall,
                            color = ColorTertiary.copy(alpha = 0.65f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}