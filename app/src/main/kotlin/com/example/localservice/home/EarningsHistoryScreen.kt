package com.example.localservice.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localservice.ui.*

data class EarningsItem(
    val amount: Int,
    val date: String,
    val time: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EarningsHistoryScreen(
    onBack: () -> Unit
) {

    val history = listOf(
        EarningsItem(140, "15 May 2026", "07:42 PM"),
        EarningsItem(158, "15 May 2026", "12:11 PM"),
        EarningsItem(200, "15 May 2026", "12:09 AM"),
        EarningsItem(100, "13 May 2026", "11:53 AM"),
        EarningsItem(340, "12 May 2026", "08:11 PM"),
        EarningsItem(210, "11 May 2026", "04:33 PM")
    )

    val total = history.sumOf { it.amount }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorAppBg)
    ) {

        TopAppBar(
            title = {

                Text(
                    text = "Earnings History",
                    color = ColorSecondary,
                    fontWeight = FontWeight.Black
                )
            },
            navigationIcon = {

                IconButton(
                    onClick = onBack
                ) {

                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = ColorTertiary.copy(alpha = 0.6f)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = ColorAppBg
            )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            item {

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = RoundedCornerShape(34.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = ColorSecondary
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(28.dp),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "TOTAL EARNINGS",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "₹${String.format("%,d", total)}",
                            fontSize = 44.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            letterSpacing = (-2).sp
                        )
                    }
                }
            }

            items(history) { item ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = ColorSurface
                    ),
                    border = BorderStroke(
                        1.dp,
                        ColorTertiary.copy(alpha = 0.05f)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {

                    Row(
                        modifier = Modifier.padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = ColorSecondary.copy(alpha = 0.08f),
                            modifier = Modifier.size(54.dp)
                        ) {

                            Box(
                                contentAlignment = Alignment.Center
                            ) {

                                Icon(
                                    Icons.Default.History,
                                    contentDescription = null,
                                    tint = ColorSecondary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(18.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = item.date,
                                style = MaterialTheme.typography.titleMedium,
                                color = ColorTertiary
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = item.time,
                                style = MaterialTheme.typography.bodySmall,
                                color = ColorTertiary.copy(alpha = 0.5f)
                            )
                        }

                        Text(
                            text = "₹${item.amount}",
                            color = ColorSecondary,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}