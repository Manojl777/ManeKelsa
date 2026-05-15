package com.example.localservice.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localservice.ui.*

@Composable
fun MyAccountScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorAppBg)
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "My Account",
            style = MaterialTheme.typography.displaySmall,
            color = ColorPrimary
        )

        Spacer(modifier = Modifier.height(28.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier.size(120.dp)
                ) {

                    Surface(
                        shape = CircleShape,
                        color = ColorSecondary,
                        modifier = Modifier.fillMaxSize()
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "M",
                                fontSize = 42.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        }
                    }

                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF22C55E),
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.BottomEnd)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                Icons.Default.Verified,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Manoj Bhat",
                    style = MaterialTheme.typography.headlineMedium,
                    color = ColorTertiary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Resident User",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ColorTertiary.copy(alpha = 0.6f)
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(
                containerColor = ColorSurface
            ),
            border = BorderStroke(
                1.dp,
                ColorTertiary.copy(alpha = 0.06f)
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        shape = CircleShape,
                        color = ColorPrimary.copy(alpha = 0.10f),
                        modifier = Modifier.size(52.dp)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = ColorPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {

                        Text(
                            text = "Full Name",
                            fontSize = 12.sp,
                            color = ColorTertiary.copy(alpha = 0.5f)
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Manoj Bhat",
                            fontWeight = FontWeight.Bold,
                            color = ColorTertiary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(
                    color = ColorTertiary.copy(alpha = 0.06f)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        shape = CircleShape,
                        color = ColorSecondary.copy(alpha = 0.10f),
                        modifier = Modifier.size(52.dp)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                Icons.Default.Call,
                                contentDescription = null,
                                tint = ColorSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {

                        Text(
                            text = "Phone Number",
                            fontSize = 12.sp,
                            color = ColorTertiary.copy(alpha = 0.5f)
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "+91 9876543210",
                            fontWeight = FontWeight.Bold,
                            color = ColorTertiary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(
                    color = ColorTertiary.copy(alpha = 0.06f)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF3B82F6).copy(alpha = 0.10f),
                        modifier = Modifier.size(52.dp)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color(0xFF3B82F6)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {

                        Text(
                            text = "Location",
                            fontSize = 12.sp,
                            color = ColorTertiary.copy(alpha = 0.5f)
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Bangalore, Karnataka",
                            fontWeight = FontWeight.Bold,
                            color = ColorTertiary
                        )
                    }
                }
            }
        }
    }
}